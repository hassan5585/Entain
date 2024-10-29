plugins {
    id(libs.plugins.entain.library.base.get().pluginId)
    kotlin("plugin.serialization")
}

android {
    namespace = "tech.mujtaba.entain.feature.race.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(project(":feature:race:domain"))
}
