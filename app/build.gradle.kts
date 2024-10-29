plugins {
    id(libs.plugins.entain.app.get().pluginId)
}

android {
    namespace = "tech.mujtaba.entain.app"
    defaultConfig {
        applicationId = "tech.mujtaba.entain"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":core:ui"))
    implementation(project(":feature:race:ui"))
    implementation(project(":feature:race:domain"))
    implementation(project(":feature:race:data"))
    implementation(project(":feature:race:navigation"))
}
