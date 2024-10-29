plugins {
    `kotlin-dsl`
}

kotlin {
   jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.jetbrains.kotlin.android.plugin)
    compileOnly(libs.jetbrains.kotlin.stdlib)
    compileOnly(libs.jetbrains.kotlin.dsl.plugin)
}

gradlePlugin {
    plugins {
        register("entainAppPlugin") {
            id = "tech.mujtaba.entain.plugin.app"
            implementationClass = "tech.mujtaba.entain.plugins.convention.EntainAppPlugin"
        }
        register("entainLibraryPlugin") {
            id = "tech.mujtaba.entain.plugin.library"
            implementationClass = "tech.mujtaba.entain.plugins.convention.EntainLibraryPlugin"
        }
        register("entainUiLibraryPlugin") {
            id = "tech.mujtaba.entain.plugin.library-ui"
            implementationClass = "tech.mujtaba.entain.plugins.convention.EntainUiLibraryPlugin"
        }
    }
}