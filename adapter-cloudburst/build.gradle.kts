plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // Cloudburst 基于 Nukkit API，使用 Nukkit 快照以获得最新编译期接口
    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")
}
