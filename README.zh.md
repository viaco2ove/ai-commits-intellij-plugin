<div align="center">
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits">
<img src="./src/main/resources/META-INF/pluginIcon.svg" width="200" height="200" alt="logo"/>
</a></a>
</div>
<h1 align="center">Ai Commits Plus</h1>
<p align="center">适用于 IntelliJ 基础版 IDE 和 Android Studio 的 Ai Commits Plus。</p>
<p align="center">
<a href="https://actions-badge.atrox.dev/blarc/ai-commits-intellij-plugin/goto?ref=main"><img alt="构建状态" src="https://img.shields.io/endpoint.svg?url=https%3A%2F%2Factions-badge.atrox.dev%2Fblarc%2Fai-commits-intellij-plugin%2Fbadge%3Fref%3Dmain&style=popout-square" /></a>
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits"><img src="https://img.shields.io/jetbrains/plugin/r/stars/21335？style=flat-square"></a>
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits"><img src="https://img.shields.io/jetbrains/plugin/d/21335-ai-commits.svg？style=flat-square"></a>
<a href="https://plugins.jetbrains.com/plugin/21335-ai-commits"><img src="https://img.shields.io/jetbrains/plugin/v/21335-ai-commits.svg？style=flat-square"></a></p>
<br>

- [描述](#描述)
- [功能](#功能)
- [兼容性](#兼容性)
- [安装](#安装)
- [从压缩包安装](#从压缩包安装
  [//]: # （[演示]（#demo）
## 描述

“Ai Commits Plus”是一款插件，它通过使用 Git 差异和大型语言模型来生成您的提交信息。要开始使用，请先安装该插件，并在插件设置中配置一个语言模型 API 客户端：<kbd>设置</kbd> > <kbd>工具</kbd> > <kbd>Ai Commits Plus</kbd>
## 特点/功能
- 通过语言模型从 git 差异中生成提交消息
- 仅从提交对话框中选定的文件和行计算差异
- 创建用于生成提交消息的自定义提示
- 使用预定义变量和提示来自定义提示
- 支持 Git 和 Subversion 作为版本控制系统。

## Supported models （支持的模型）

- Amazon Bedrock
- Anthropic
- Azure Open AI
- Claude Code (via CLI)
- Codex CLI (via CLI)
- Gemini Google AI
- Gemini Vertex AI
- GitHub Models
- Hugging Face
- Mistral AI
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



### 方式二：通过IDE Plugin Marketplace（未来支持）

插件正在审核中，预计很快可以通过官方插件市场安装。

## 📖 使用说明

### 1. 首次设置

1. **启动Claude Code Tool Window**
   - 在IDEA右侧面板找到 `Claude Code` 工具窗口
   - 如果没有看到，通过 `View` → `Tool Windows` → `Claude Code` 打开

2. **配置Claude Code路径**
   - 在工具窗口的 "Claude Code路径" 输入框中输入Claude CLI的完整路径
   - 例如: `/usr/local/bin/claude` 或 `C:\\Users\\username\\AppData\\Local\\claude\\claude.exe`

3. **建立连接**
   - 点击 `连接Claude Code` 按钮
   - 等待连接状态变为 "已连接"
