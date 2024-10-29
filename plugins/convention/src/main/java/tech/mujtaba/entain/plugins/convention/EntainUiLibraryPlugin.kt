package tech.mujtaba.entain.plugins.convention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.plugin

/**
 * Apply this plugin to a library module to get access to basic compose and material3 apis
 */
class EntainUiLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin(EntainLibraryPlugin::class)
            }
            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExtensionVersion").get().toString()
                }
            }
            dependencies {
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-activity-compose").get())
                add(CONFIG_IMPLEMENTATION, platform(libs.findLibrary("androidx-compose-bom").get()))
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-ui").get())
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-ui-graphics").get())
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-ui-tooling").get())
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-ui-tooling-preview").get())
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-material3").get())
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("androidx-navigation-compose").get())
                add(CONFIG_IMPLEMENTATION, libs.findLibrary("dagger-hilt-navigation-compose").get())
                add(CONFIG_DEBUG_IMPLEMENTATION, libs.findLibrary("androidx-ui-tooling").get())
                add(CONFIG_DEBUG_IMPLEMENTATION, libs.findLibrary("androidx-ui-test-manifest").get())
            }
        }
    }
}