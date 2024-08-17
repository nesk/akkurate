package dev.nesk.akkurate.gradle

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures all the available targets on a project, with the ability to ignore some of them.
 *
 * Some projects can't support all the targets, due to their dependencies. This function enables
 * easy target configuration from a project to another. By default, all the targets are added to
 * the project, and the conflicting ones can be ignored.
 *
 * @param ignoredTargets The targets to ignore.
 */
fun KotlinMultiplatformExtension.configureTargets(vararg ignoredTargets: IgnoredTarget) {
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

    if (IgnoredTarget.ANDROID_NATIVE !in ignoredTargets) {
        androidNativeArm32()
        androidNativeArm64()
        androidNativeX86()
        androidNativeX64()
    }

    if (IgnoredTarget.WASM_JS !in ignoredTargets) {
        @OptIn(ExperimentalWasmDsl::class)
        wasmJs { nodejs() }
    }

    if (IgnoredTarget.WASM_WASI !in ignoredTargets) {
        @OptIn(ExperimentalWasmDsl::class)
        wasmWasi { nodejs() }
    }

    if (IgnoredTarget.WATCHOS_DEVICE_ARM64 !in ignoredTargets) {
        watchosDeviceArm64()
    }
}

enum class IgnoredTarget {
    WASM_JS, WASM_WASI, ANDROID_NATIVE, WATCHOS_DEVICE_ARM64
}
