plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // Nukkit-MOT 官方依赖（来自 https://repo.lanink.cn）
    compileOnly("cn.nukkit:Nukkit:MOT-SNAPSHOT")
}
