@file:OptIn(ExperimentalWasmDsl::class)

import dev.nesk.akkurate.gradle.KmpTarget
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("akkurate.base.identity")
    id("akkurate.base.repositories")
    id("akkurate.base.kotlin-compilation")
    id("com.adarshr.test-logger")
}

extensions.create<KmpLibraryPluginExtension>("kmpLibrary").apply {
    ignoredTargets.configureEach {
        val ignoredNames = when (this) {
            KmpTarget.WasmJs -> listOf("wasmJs")
            KmpTarget.WasmWasi -> listOf("wasmWasi")
            KmpTarget.AndroidNative -> listOf(
                "androidNativeArm32",
                "androidNativeArm64",
                "androidNativeX86",
                "androidNativeX64",
            )

            KmpTarget.WatchosDeviceArm64 -> listOf("watchosDeviceArm64")
        }
        kotlin.targets.removeIf { it.targetName in ignoredNames }
    }
}

kotlin {
    explicitApi()

    iosArm64()
    iosSimulatorArm64()
    iosX64()
    js(IR) { nodejs() }
    jvm()
    linuxArm64()
    linuxX64()
    macosArm64()
    macosX64()
    mingwX64()
    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()
    wasmJs { nodejs() }
    wasmWasi { nodejs() }
    watchosDeviceArm64()

    sourceSets {
        all {
            languageSettings {
                // See: https://kotlinlang.org/docs/multiplatform-dsl-reference.html#language-settings
                languageVersion = "1.9"
                apiVersion = "1.9"
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

interface KmpLibraryPluginExtension {
    val ignoredTargets: DomainObjectSet<KmpTarget>

    @Suppress("unused")
    fun ignoreTargets(block: KmpLibraryTargetsScope.() -> Unit) {
        object : KmpLibraryTargetsScope {
            override fun wasmJs() = ignoredTargets.add(KmpTarget.WasmJs)

            override fun wasmWasi() = ignoredTargets.add(KmpTarget.WasmWasi)

            override fun androidNative() = ignoredTargets.add(KmpTarget.AndroidNative)

            override fun watchosDeviceArm64() = ignoredTargets.add(KmpTarget.WatchosDeviceArm64)
        }.block()
    }
}

interface KmpLibraryTargetsScope {
    fun wasmJs(): Boolean
    fun wasmWasi(): Boolean
    fun androidNative(): Boolean
    fun watchosDeviceArm64(): Boolean
}