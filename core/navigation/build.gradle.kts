plugins {
    id(libs.plugins.entain.library.base.get().pluginId)
}

android {
    namespace = "tech.mujtaba.entain.core.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
}
