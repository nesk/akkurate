/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
