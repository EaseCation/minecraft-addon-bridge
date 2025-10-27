plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
    implementation(project(":addon-bridge-core"))
    implementation(project(":addon-pack-tools"))
    // include adapters so runner can instantiate them at runtime
    implementation(project(":adapter-cloudburst"))
    implementation(project(":adapter-easecation"))
    implementation(project(":adapter-pm1e"))
    implementation(project(":adapter-mot"))
    implementation(project(":adapter-pnx"))
    // Use compileOnly to avoid bundling server classes; provided by Nukkit at runtime
    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    // 产物名固定为 nukkit-addon-bridge.jar（无版本、无分类）
    archiveBaseName.set("nukkit-addon-bridge")
    archiveClassifier.set("")
    archiveVersion.set("")
}

tasks.named("build") {
    dependsOn(tasks.named("shadowJar"))
}

// 在 shadowJar 完成后，将插件拷贝到本仓库的本地工作区（workspace/*/plugins）
val installToWorkspaces = tasks.register("installToWorkspaces") {
    dependsOn(tasks.named("shadowJar"))
    doLast {
        val shadow = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar").get()
        val jarFile = shadow.archiveFile.get().asFile
        val targets = listOf(
            project.rootDir.resolve("workspace/cloudburst/plugins"),
            project.rootDir.resolve("workspace/pnx/plugins"),
            project.rootDir.resolve("workspace/pm1e/plugins"),
            project.rootDir.resolve("workspace/mot/plugins"),
            project.rootDir.resolve("workspace/easecation/plugins"),
        )
        targets.forEach { dir ->
            dir.mkdirs()
            copy {
                from(jarFile)
                into(dir)
                rename { "nukkit-addon-bridge.jar" }
            }
        }
        logger.lifecycle("Copied ${jarFile.name} to: ${targets.joinToString { it.absolutePath }}")
    }
}

// 运行 shadowJar 时自动执行复制
tasks.named("shadowJar") { finalizedBy(installToWorkspaces) }
