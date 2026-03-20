# 按这个顺序配置gradle(IntelliJ)：

1. IntelliJ 里：Settings -> Build, Execution, Deployment -> Build Tools -> Gradle
   把 Use Gradle from 设成 Gradle Wrapper
   把 Gradle JVM 设成 D:\Users\viaco\.jdks\jdk-21.0.10+7
2. File -> Project Structure -> Project
   把 Project SDK 也设成 JDK 21
3. 完全关闭 IntelliJ，再打开项目，Reload All Gradle Projects

如果还是报同样错误，就清缓存（这一步能排除 .gradle 干扰）：

- 删除项目内 .gradle
- 删除 D:\Users\viaco\.gradle\caches
- 删除 D:\Users\viaco\.gradle\daemon

请 确认项目配置是正确的：
- gradle/wrapper/gradle-wrapper.properties 指向 9.4.0
- gradle.properties 里 javaVersion = 21
- .idea/gradle.xml 已是 DEFAULT_WRAPPED 且 gradleJvm = #JAVA_HOME

按下面改：

1. Settings -> Build, Execution, Deployment -> Build Tools -> Gradle
2. 把 Gradle JVM 改成  Project SDK ms-21 ,直接与Project Structure 一致
3. 把 Gradle user home 改回默认（清空该项或改成 D:\Users\viaco\.gradle）
4. 点 Apply，再 Reload All Gradle Projects
5. 如果还报错，关掉 IDE 重开（让新 JVM 生效）

下载jdk-21
D:\Users\xxx\.jdks\jdk-21.0.10+7
