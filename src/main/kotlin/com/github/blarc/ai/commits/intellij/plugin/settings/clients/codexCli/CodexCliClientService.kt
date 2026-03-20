package com.github.blarc.ai.commits.intellij.plugin.settings.clients.codexCli

import com.github.blarc.ai.commits.intellij.plugin.AICommitsBundle.message
import com.github.blarc.ai.commits.intellij.plugin.notifications.Notification
import com.github.blarc.ai.commits.intellij.plugin.notifications.sendNotification
import com.github.blarc.ai.commits.intellij.plugin.settings.clients.LlmCliClientService
import com.github.blarc.ai.commits.intellij.plugin.wrap
import com.intellij.icons.AllIcons
import com.intellij.openapi.application.EDT
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.asContextElement
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.SystemInfo
import com.intellij.ui.components.JBLabel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.TimeUnit

@Service(Service.Level.APP)
class CodexCliClientService(private val cs: CoroutineScope) : LlmCliClientService<CodexCliClientConfiguration>(cs) {

    companion object {
        @JvmStatic
        fun getInstance(): CodexCliClientService = service()
    }

    private val logger = Logger.getInstance(CodexCliClientService::class.java)

    override suspend fun executeCli(
        client: CodexCliClientConfiguration,
        prompt: String
    ): Result<String> {
        return executeCodexCli(client, prompt)
    }

    private suspend fun executeCodexCli(
        client: CodexCliClientConfiguration,
        prompt: String
    ): Result<String> = withContext(Dispatchers.IO) {
        val resolvedPath = try {
            resolveCliPath(client)
        } catch (e: IllegalStateException) {
            return@withContext Result.failure(e)
        }
        val file = File(resolvedPath)
        if (!isExecutable(file)) {
            return@withContext Result.failure(
                IllegalStateException(message("codexCli.pathNotFound", resolvedPath))
            )
        }

        val command = mutableListOf(
            resolvedPath,
            "exec",
            "--skip-git-repo-check"
        )

        if (client.modelId.isNotBlank()) {
            command.add("--model")
            command.add(client.modelId)
        }

        val reasoningValue = normalizeReasoningLevel(resolveReasoningLevel(client))
        if (reasoningValue.isNotBlank()) {
            command.add("--config")
            command.add("model_reasoning_effort=\"$reasoningValue\"")
        }

        command.add("--")
        command.add(prompt)

        try {
            logger.info(
                "Codex CLI start: path=$resolvedPath, model=${client.modelId}, " +
                    "reasoning=${resolveReasoningLevel(client)}, timeout=${client.timeout}s, " +
                    "prompt=${truncateForLog(prompt)}"
            )
            val process = ProcessBuilder(command)
                .redirectErrorStream(true)
                .start()

            // Close stdin immediately to prevent CLI from waiting for input.
            process.outputStream.close()

            // Read output in a separate thread to prevent buffer deadlock.
            val outputFuture = java.util.concurrent.CompletableFuture.supplyAsync {
                process.inputStream.bufferedReader().readText()
            }

            val completed = process.waitFor(client.timeout.toLong(), TimeUnit.SECONDS)
            if (!completed) {
                process.destroyForcibly()
                outputFuture.cancel(true)
                logger.warn("Codex CLI timeout after ${client.timeout}s")
                return@withContext Result.failure(
                    IllegalStateException(message("codexCli.timeout"))
                )
            }

            val output = try {
                outputFuture.get(5, TimeUnit.SECONDS)
            } catch (e: Exception) {
                val cause = (e as? java.util.concurrent.ExecutionException)?.cause ?: e
                logger.warn("Codex CLI output read failed: ${cause.message}", cause)
                return@withContext Result.failure(
                    IllegalStateException("Failed to read CLI output: ${cause.message}", cause)
                )
            }

            if (process.exitValue() != 0) {
                logger.warn("Codex CLI exit code ${process.exitValue()}: ${truncateForLog(output)}")
                return@withContext Result.failure(
                    IllegalStateException("CLI exited with code ${process.exitValue()}: $output")
                )
            }

            val messageText = parseCodexResponse(output)
            logger.info("Codex CLI success: output=${truncateForLog(output)}, message=${truncateForLog(messageText)}")
            Result.success(messageText)
        } catch (e: Exception) {
            logger.warn("Codex CLI failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    private fun resolveCliPath(client: CodexCliClientConfiguration): String {
        val configuredPath = client.cliPath.trim()
        if (configuredPath.isNotBlank()) {
            resolveExecutablePath(configuredPath)?.let { return it }
            throw IllegalStateException(message("codexCli.pathNotFound", configuredPath))
        }

        findOnPath()?.let { return it }
        throw IllegalStateException(message("codexCli.cliNotFound"))
    }

    private fun isExecutable(file: File): Boolean {
        return if (SystemInfo.isWindows) {
            file.isFile && isWindowsExecutable(file)
        } else {
            file.isFile && file.canExecute()
        }
    }

    private fun resolveExecutablePath(path: String): String? {
        val file = File(path)
        if (isExecutable(file)) {
            return file.absolutePath
        }

        if (SystemInfo.isWindows && file.extension.isBlank()) {
            val candidates = listOf("$path.cmd", "$path.exe", "$path.bat", "$path.com")
            for (candidate in candidates) {
                val candidateFile = File(candidate)
                if (isExecutable(candidateFile)) {
                    return candidateFile.absolutePath
                }
            }
        }

        return null
    }

    private fun truncateForLog(text: String, limit: Int = 300): String {
        val trimmed = text.replace("\n", "\\n").replace("\r", "\\r")
        return if (trimmed.length <= limit) trimmed else trimmed.take(limit) + "...(len=${trimmed.length})"
    }

    private fun findOnPath(): String? {
        val pathValue = System.getenv("PATH") ?: return null
        val candidates = if (SystemInfo.isWindows) {
            listOf("codex.cmd", "codex.exe", "codex.bat", "codex.com")
        } else {
            listOf("codex")
        }
        val paths = pathValue.split(File.pathSeparatorChar)
        for (dir in paths) {
            if (dir.isBlank()) continue
            for (name in candidates) {
                val file = File(dir, name)
                if (isExecutable(file)) {
                    return file.absolutePath
                }
            }
        }
        return null
    }

    private fun isWindowsExecutable(file: File): Boolean {
        val extension = file.extension.lowercase()
        return extension in setOf("exe", "cmd", "bat", "com")
    }

    private fun parseCodexResponse(output: String): String {
        val lines = output.lines()
            .map { it.trimEnd() }
            .filter { it.isNotBlank() }

        if (lines.isEmpty()) {
            throw IllegalStateException("No result from Codex CLI")
        }

        val tokensIndex = lines.indexOfFirst { it.lowercase().startsWith("tokens used") }
        val relevantLines = if (tokensIndex > 0) lines.subList(0, tokensIndex) else lines

        val lastCodexIndex = relevantLines.indexOfLast { it.equals("codex", ignoreCase = true) }
        val contentLines = if (lastCodexIndex >= 0 && lastCodexIndex + 1 < relevantLines.size) {
            relevantLines.subList(lastCodexIndex + 1, relevantLines.size)
        } else {
            relevantLines
        }

        val candidate = contentLines
            .filterNot { it.equals("user", ignoreCase = true) }
            .filterNot { it.equals("codex", ignoreCase = true) }
            .filterNot { it.startsWith("OpenAI Codex", ignoreCase = true) }
            .filterNot { it.startsWith("workdir:", ignoreCase = true) }
            .filterNot { it.startsWith("model:", ignoreCase = true) }
            .filterNot { it.startsWith("provider:", ignoreCase = true) }
            .filterNot { it.startsWith("approval:", ignoreCase = true) }
            .filterNot { it.startsWith("sandbox:", ignoreCase = true) }
            .filterNot { it.startsWith("reasoning", ignoreCase = true) }
            .filterNot { it.startsWith("session id:", ignoreCase = true) }
            .filterNot { it.startsWith("mcp startup:", ignoreCase = true) }
            .joinToString("\n")
            .trim()

        if (candidate.isBlank()) {
            throw IllegalStateException("No result from Codex CLI")
        }

        return candidate
    }

    private fun normalizeReasoningLevel(level: String): String {
        if (level.isBlank()) {
            return ""
        }
        return level.trim()
            .lowercase()
            .replace(" ", "_")
    }

    private fun resolveReasoningLevel(client: CodexCliClientConfiguration): String {
        val allowedLevels = if (client.modelId == "gpt-5.1-codex-mini") {
            listOf("Medium", "High")
        } else {
            CodexCliClientConfiguration.REASONING_LEVELS
        }
        return if (client.reasoningLevel in allowedLevels) {
            client.reasoningLevel
        } else {
            CodexCliClientConfiguration.DEFAULT_REASONING_LEVEL
        }
    }

    override fun verifyConfiguration(client: CodexCliClientConfiguration, label: JBLabel) {
        label.text = message("settings.verify.running")
        label.icon = AllIcons.General.InlineRefresh
        cs.launch(ModalityState.current().asContextElement()) {
            val result = executeCodexCli(client, "Say 'OK' in exactly one word")
            withContext(Dispatchers.EDT) {
                result.fold(
                    onSuccess = {
                        label.text = message("settings.verify.valid")
                        label.icon = AllIcons.General.InspectionsOK
                    },
                    onFailure = { error ->
                        val errorMessage = error.message ?: message("unknown-error")
                        label.text = message("settings.verify.invalid", errorMessage).wrap(60)
                        label.icon = AllIcons.General.InspectionsError
                        sendNotification(Notification.unsuccessfulRequest(errorMessage))
                    }
                )
            }
        }
    }
}
