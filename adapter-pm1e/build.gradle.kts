plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // PM1E 官方建议：使用服务器生成的 patched.jar（位于 server/.cache）。
    // 提供两种方式告知路径：
    // 1) Gradle 属性：-PPM1E_PATCHED_JAR=/absolute/path/to/patched.jar
    // 2) 默认位置：adapter-pm1e/lib/patched.jar（相对本模块）

    val propPath = providers.gradleProperty("PM1E_PATCHED_JAR").orNull
        ?: System.getenv("PM1E_PATCHED_JAR")
    val defaultFile = layout.projectDirectory.file("lib/patched.jar").asFile

    if (propPath != null && propPath.isNotBlank()) {
        compileOnly(files(propPath))
        logger.lifecycle("PM1E patched.jar 使用路径(属性/环境)：$propPath")
    } else if (defaultFile.exists()) {
        compileOnly(files(defaultFile))
        logger.lifecycle("PM1E patched.jar 使用默认文件：${defaultFile.absolutePath}")
    } else {
        logger.lifecycle("未提供 PM1E patched.jar（可通过 -PPM1E_PATCHED_JAR 或放置到 adapter-pm1e/lib/patched.jar）")
    }
}
