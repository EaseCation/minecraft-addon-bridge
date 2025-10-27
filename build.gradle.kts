plugins { /* root project: no global plugins */ }

allprojects {
    group = "net.easecation.bridge"
    version = "0.1.0"
    repositories {
        mavenCentral()
        // Cloudburst/Nukkit snapshots, etc.
        maven("https://repo.opencollab.dev/maven-releases")
        maven("https://repo.opencollab.dev/maven-snapshots")
        maven("https://jitpack.io")
        // Nukkit-MOT 官方提供仓库
        maven("https://repo.lanink.cn/repository/maven-public/")
    }
}

subprojects {
    // Apply Java Library plugin to all subprojects
    plugins.apply("java-library")

    // Configure Java toolchain and sources jar via extension to avoid Kotlin DSL compile-time plugin access issues
    extensions.configure<org.gradle.api.plugins.JavaPluginExtension> {
        toolchain {
            languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(21))
        }
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    // Keep builds offline-friendly: no external test deps
    tasks.withType<Test>().configureEach {
        enabled = false
    }
}
