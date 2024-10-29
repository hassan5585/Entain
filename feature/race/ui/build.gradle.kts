plugins {
    id(libs.plugins.entain.library.ui.get().pluginId)
}

android {
    namespace = "tech.mujtaba.entain.feature.race.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:race:domain"))
    implementation(project(":feature:race:navigation"))
}
