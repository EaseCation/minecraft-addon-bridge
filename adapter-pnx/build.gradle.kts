plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // PowerNukkitX（官方建议使用 JitPack 坐标）
    // 仅编译期依赖，运行期由服务器提供
    compileOnly("com.github.PowerNukkitX:PowerNukkitX:master-SNAPSHOT")

    // FastReflection - PNX使用的反射加速库
    // 仅编译期依赖，运行期由PNX提供
    compileOnly("com.github.AllayMC:fast-reflection:8733a599fa")

    // ByteBuddy for runtime class generation
    implementation("net.bytebuddy:byte-buddy:1.14.10")
}
