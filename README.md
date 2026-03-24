<div align="center">
    <a href="https://plugins.jetbrains.com/plugin/21335-ai-commits">
        <img src="./src/main/resources/META-INF/pluginIcon.svg" width="200" height="200" alt="logo"/>
    </a>
</div>
<h1 align="center">Ai Commits Plus</h1>
<p align="center">Ai Commits Plus for IntelliJ based IDEs/Android Studio.</p>

<p align="center">
<a href="https://actions-badge.atrox.dev/blarc/ai-commits-intellij-plugin/goto?ref=main"><img alt="Build Status" src="https://img.shields.io/endpoint.svg?url=https%3A%2F%2Factions-badge.atrox.dev%2Fblarc%2Fai-commits-intellij-plugin%2Fbadge%3Fref%3Dmain&style=popout-square" /></a>
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits"><img src="https://img.shields.io/jetbrains/plugin/r/stars/21335?style=flat-square"></a>
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits"><img src="https://img.shields.io/jetbrains/plugin/d/21335-ai-commits.svg?style=flat-square"></a>
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits"><img src="https://img.shields.io/jetbrains/plugin/v/21335-ai-commits.svg?style=flat-square"></a>
</p>
<br>

- [Description](#description)
- [Features](#features)
- [Compatibility](#compatibility)
- [Install](#install)
- [Installation from zip](#installation-from-zip)

[//]: # (- [Demo]&#40;#demo&#41;)

## Description

Ai Commits Plus is a plugin that generates your commit messages by using git diff and LLMs. To get started, install the
plugin and configure a LLM API client in plugin's settings: <kbd>Settings</kbd> > <kbd>Tools</kbd> > <kbd>Ai Commits Plus</kbd>

## Features

- Generate commit message from git diff using LLM
- Compute diff only from the selected files and lines in the commit dialog
- Create your own prompt for commit message generation
- Use predefined variables and hint to customize your prompt
- Supports Git and Subversion as version control systems.

## Supported models

- Amazon Bedrock
- Anthropic
- Azure Open AI
- Claude Code (via CLI)
- Codex CLI (via CLI)
- DeepSeek
- Gemini Google AI
- Gemini Vertex AI
- GitHub Models
- Hugging Face
- Mistral AI
- LM Studio
- Open AI
- Ollama
- Qianfan (Ernie)

The plugin is implemented in a generic way and uses [langchain4j](https://github.com/langchain4j/langchain4j) for creating LLM API clients. If you would like to use some other LLM model that is supported by langchain4j, please make a feature request in GitHub issues.

## Demo

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="./screenshots/plugin-dark.gif">
  <source media="(prefers-color-scheme: light)" srcset="./screenshots/plugin-white.gif">
  <img alt="Demo." src="./screenshots/plugin-white.gif">
</picture>

## Compatibility

IntelliJ IDEA, PhpStorm, WebStorm, PyCharm, RubyMine, AppCode, CLion, GoLand, DataGrip, Rider, MPS, Android Studio,
DataSpell, Code With Me

## Install

<a href="https://plugins.jetbrains.com/embeddable/install/21335">
<img src="https://user-images.githubusercontent.com/12044174/123105697-94066100-d46a-11eb-9832-338cdf4e0612.png" width="300"/>
</a>

Or you could install it inside your IDE:

For Windows & Linux: <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search
for "Ai Commits Plus"</kbd> > <kbd>Install Plugin</kbd> > <kbd>Restart IntelliJ IDEA</kbd>

For Mac: <kbd>IntelliJ IDEA</kbd> > <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search
for "Ai Commits Plus"</kbd> > <kbd>Install Plugin</kbd>  > <kbd>Restart IntelliJ IDEA</kbd>

### Installation from zip

1. Download zip from [releases](https://github.com/Blarc/ai-commits-intellij-plugin/releases)
2. Import to IntelliJ: <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Cog</kbd> > <kbd>Install plugin from
   disk...</kbd>
3. Set LLM client configuration in plugin's settings: <kbd>Settings</kbd> > <kbd>Tools</kbd> > <kbd>Ai Commits Plus</kbd>

[//]: # (## Demo)

[//]: # ()

[//]: # (![demo.gif]&#40;./screenshots/plugin2.gif&#41;)

## Support

* Star the repository
* [Buy me a coffee](https://ko-fi.com/blarc)
* [Rate the plugin](https://plugins.jetbrains.com/plugin/21335-ai-commits)
* [Share the plugin](https://plugins.jetbrains.com/plugin/21335-ai-commits)
* [Sponsor me](https://github.com/sponsors/Blarc)

## Change log

Please see [CHANGELOG](CHANGELOG.md) for more information what has changed recently.

## Contributing

Please see [CONTRIBUTING](CONTRIBUTING.md) for details.

## Acknowledgements

- Inspired by Nutlope's [AICommits](https://github.com/Nutlope/aicommits).
- [openai-kotlin](https://github.com/aallam/openai-kotlin) for OpenAI API client.
- [langchain4j](https://github.com/langchain4j/langchain4j) for LLM API clients.

## License

Please see [LICENSE](LICENSE) for details.

## Star History

<a href="https://star-history.com/#Blarc/ai-commits-intellij-plugin&Date">
 <picture>
   <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=Blarc/ai-commits-intellij-plugin&type=Date&theme=dark" />
   <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/svg?repos=Blarc/ai-commits-intellij-plugin&type=Date" />
   <img alt="Star History Chart" src="https://api.star-history.com/svg?repos=Blarc/ai-commits-intellij-plugin&type=Date" />
 </picture>
</a>

###Method 2: Through the IDE Plugin Marketplace (future support)
The plug-in is under review and is expected to be installed through the official plug-in market soon.
##Instructions for use
### 1. first time setup
1. ** Start Claude Code Tool Window**
   - Find the Claude Code tool window on the right panel of IDEA.
   - If not, open it via View → Tool Windows → Claude Code.
2. ** Configure Claude Code path **
   - Enter the full path to the Claude CLI in the "Claude Code Path" input box in the tool window
   - For example: `/usr/local/bin/claude` or `C:\\Users\\username\\AppData\\\Local\\\claude\\claude.exe`
3. ** Establish connection **
   - Click the `Connect Claude Code` button
   - Wait for the connection status to change to "Connected"


###Development steps
1. ** Import projects **
   ```bash
   git clone <repository-url>
   cd claude_plugin
   ./gradlew idea
   ```
2. ** Running development examples **
   ```bash
   ./gradlew runIde
   ```
   This will launch an IDEA instance with a plug-in for testing
3. ** Build and test **
   If you want to clean up the cache
```bash
   .\ gradlew.bat clean build--no-daemon--refresh-dependencies
  .\ gradlew.bat build --no-daemon
```
   ```bash
   #Build plug-ins
   .\ gradlew.bat buildPlugin --no-daemon
   
   ./gradlew buildPlugin
   
   #Run the test
   ./gradlew test
   
   #Verify Plug-ins
   ./gradlew verifyPlugin
   ``` 