package com.github.blarc.ai.commits.intellij.plugin

import com.github.blarc.ai.commits.intellij.plugin.notifications.Notification
import com.github.blarc.ai.commits.intellij.plugin.settings.ProjectSettings
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vcs.VcsDataKeys

class AICommitAction : AnAction(), DumbAware {
    private val logger = Logger.getInstance(AICommitAction::class.java)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }

    override fun update(e: AnActionEvent) {
        // Only enable this action when the commit dialog is open (COMMIT_WORKFLOW_HANDLER is available)
        val commitWorkflowHandler = e.getData(VcsDataKeys.COMMIT_WORKFLOW_HANDLER)
        val hasActiveLlmClient = e.project?.service<ProjectSettings>()?.getSplitButtonActionSelectedOrActiveLLMClient() != null

        e.presentation.isEnabledAndVisible = commitWorkflowHandler != null && hasActiveLlmClient
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val projectSettings = project.service<ProjectSettings>()
        val splitSelectedId = projectSettings.splitButtonActionSelectedLLMClientId
        val activeClient = projectSettings.getActiveLLMClientConfiguration()
        val llmClient = projectSettings.getSplitButtonActionSelectedOrActiveLLMClient()

        if (llmClient == null) {
            Notification.clientNotSet()
            return
        }

        val source = if (splitSelectedId != null && splitSelectedId == llmClient.id) {
            "split-button-selection"
        } else {
            "active-client"
        }
        logger.info(
            "AI Commit using client: source=$source, client=${llmClient.getClientName()}/${llmClient.name}," +
                " clientId=${llmClient.id}, activeClientId=${activeClient?.id}, splitSelectedId=$splitSelectedId"
        )

        llmClient.generateCommitMessageAction(e)
    }

}
