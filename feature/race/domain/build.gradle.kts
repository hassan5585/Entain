plugins {
    id(libs.plugins.entain.library.base.get().pluginId)
}

android {
    namespace = "tech.mujtaba.entain.feature.race.domain"
}

dependencies {
    implementation(project(":core:util"))
}
