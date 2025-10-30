plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // Nukkit-MOT 官方依赖（来自 https://repo.lanink.cn）
    compileOnly("cn.nukkit:Nukkit:MOT-SNAPSHOT")

    // ByteBuddy for runtime class generation (支持无限数量的自定义物品和实体)
    implementation("net.bytebuddy:byte-buddy:1.14.10")
}
