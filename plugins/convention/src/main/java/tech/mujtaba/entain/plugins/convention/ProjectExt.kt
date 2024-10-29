package tech.mujtaba.entain.plugins.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val VersionCatalog.targetSdk: Int
    get() = findVersion("targetSdk").get().toString().toInt()

internal val VersionCatalog.minSdk: Int
    get() = findVersion("minSdk").get().toString().toInt()

internal val VersionCatalog.compileSdk: Int
    get() = findVersion("compileSdk").get().toString().toInt()

internal val VersionCatalog.jdkVersion: Int
    get() = findVersion("jdkVersion").get().toString().toInt()