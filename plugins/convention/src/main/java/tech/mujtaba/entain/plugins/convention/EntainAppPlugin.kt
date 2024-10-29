package tech.mujtaba.entain.plugins.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.plugin
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

/**
 * This plugin is only to be applied to an android application module
 */
class EntainAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin("com.android.application")
                plugin("org.jetbrains.kotlin.android")
                plugin(HiltPlugin::class)
            }

            kotlinExtension.jvmToolchain(libs.jdkVersion)

            extensions.configure<BaseAppModuleExtension> {
                compileSdk = libs.compileSdk

                defaultConfig {
                    minSdk = libs.minSdk
                    targetSdk = libs.targetSdk

                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                buildFeatures {
                    compose = true
                }
                composeOptions {
                    kotlinCompilerExtensionVersion =
                        libs.findVersion("kotlinCompilerExtensionVersion").get().toString()
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            dependencies {
                add(CONFIG_DEBUG_IMPLEMENTATION, libs.findLibrary("androidx-ui-tooling").get())
                add(CONFIG_DEBUG_IMPLEMENTATION, libs.findLibrary("androidx-ui-test-manifest").get())
            }
        }
    }
}