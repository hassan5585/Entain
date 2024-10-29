package tech.mujtaba.entain.plugins.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KaptExtensionConfig

internal class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin("kotlin-kapt")
                plugin("com.google.dagger.hilt.android")
            }
            dependencies {
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("dagger-hilt-android").get())
                add(CONFIG_KAPT, libs.findLibrary("dagger-hilt-android-compiler").get())
            }

            extensions.configure<KaptExtensionConfig> {
                correctErrorTypes = true
            }
        }
    }
}