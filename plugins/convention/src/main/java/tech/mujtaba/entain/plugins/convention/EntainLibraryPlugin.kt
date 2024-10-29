package tech.mujtaba.entain.plugins.convention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.plugin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * Apply it to a library plugin to get some basic android setup, DI plumbing and test dependencies.
 */
class EntainLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin("com.android.library")
                plugin("org.jetbrains.kotlin.android")
                plugin(HiltPlugin::class)
            }

            kotlinExtension.jvmToolchain(libs.jdkVersion)

            extensions.configure<LibraryExtension> {
                compileSdk = libs.compileSdk
                defaultConfig {
                    minSdk = libs.minSdk
                }
            }

            dependencies {
                add(CONFIG_IMPLEMENTATION_TEST, libs.findLibrary("junit-jupiter-api").get())
                add(CONFIG_RUNTIME_TEST, libs.findLibrary("junit-jupiter-engine").get())
                add(CONFIG_IMPLEMENTATION_TEST, libs.findLibrary("mockk-android").get())
                add(CONFIG_IMPLEMENTATION_TEST, libs.findLibrary("mockk-agent").get())
                add(CONFIG_IMPLEMENTATION_TEST, libs.findLibrary("kotlinx-coroutines-test").get())
            }

            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}