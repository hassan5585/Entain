plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.diffplug.spotless) apply false
    alias(libs.plugins.dagger.hilt) apply false
    kotlin("plugin.serialization") version libs.versions.kotlin apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            ktlint()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
//            licenseHeaderFile(rootProject.file("spotless/copyright.kt")) In case we wanted to add a license header
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }
    }
}