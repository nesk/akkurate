@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("akkurate.base.identity")
    id("akkurate.base.repositories")
    id("akkurate.base.kotlin-compilation")
    id("com.adarshr.test-logger")
}

extensions.create<KmpLibraryPluginExtension>("component").apply {
    ignoredTargets.configureEach {
        val ignoredTargetName = this
        kotlin.targets.removeIf { it.targetName == ignoredTargetName }
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
    val ignoredTargets: DomainObjectSet<String>

    @Suppress("unused")
    fun ignoredTargets(block: KmpLibraryTargetsScope.() -> Unit) {
        object : KmpLibraryTargetsScope {
            override fun wasmJs() {
                ignoredTargets.add("wasmJs")
            }

            override fun wasmWasi() {
                ignoredTargets.add("wasmWasi")
            }

            override fun androidNative() {
                ignoredTargets.addAll(
                    listOf(
                        "androidNativeArm32",
                        "androidNativeArm64",
                        "androidNativeX86",
                        "androidNativeX64",
                    )
                )
            }

            override fun watchosDeviceArm64() {
                ignoredTargets.add("watchosDeviceArm64")
            }
        }.block()
    }
}

interface KmpLibraryTargetsScope {
    fun wasmJs()
    fun wasmWasi()
    fun androidNative()
    fun watchosDeviceArm64()
}