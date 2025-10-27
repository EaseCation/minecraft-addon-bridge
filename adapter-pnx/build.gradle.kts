plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // PowerNukkitX（官方建议使用 JitPack 坐标）
    // 仅编译期依赖，运行期由服务器提供
    compileOnly("com.github.PowerNukkitX:PowerNukkitX:master-SNAPSHOT")
}
