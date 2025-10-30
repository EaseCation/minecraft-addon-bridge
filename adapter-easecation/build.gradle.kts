plugins { `java-library` }

dependencies {
    api(project(":addon-bridge-core"))
    // EaseCation Nukkit 分支（通过 JitPack）。可用 -PeasecationNukkitTag=branch-or-tag-SNAPSHOT 覆盖。
    val ecTag = providers.gradleProperty("easecationNukkitTag").orNull ?: "master-SNAPSHOT"
    compileOnly("com.github.EaseCation:Nukkit:$ecTag")
    compileOnly("com.github.EaseCation:SynapseAPI:$ecTag")
}
