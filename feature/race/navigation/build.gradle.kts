plugins {
    id(libs.plugins.entain.library.base.get().pluginId)
}

android {
    namespace = "tech.mujtaba.entain.feature.race.navigation"
}

dependencies {
    api(project(":core:navigation"))
}
