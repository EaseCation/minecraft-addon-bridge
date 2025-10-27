pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

// Centralize repository definitions for all projects
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
  repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/maven-releases")
    maven("https://repo.opencollab.dev/maven-snapshots")
    maven("https://repo.lanink.cn/repository/maven-public/")
    maven("https://jitpack.io")
  }
}

rootProject.name = "nukkit-addon-bridge"

include(
  "addon-bridge-core",
  "addon-pack-tools",
  "adapter-cloudburst",
  "adapter-easecation",
  "adapter-pm1e",
  "adapter-mot",
  "adapter-pnx",
  "plugin-runner",
)
