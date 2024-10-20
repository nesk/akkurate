import dev.nesk.akkurate.gradle.IgnoredTarget
import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization") version "2.0.20"
}

kotlin {
    configureTargets(IgnoredTarget.JS_IR, IgnoredTarget.WASM_JS, IgnoredTarget.WASM_WASI)

    sourceSets {
        commonMain.dependencies {
            api(project(":akkurate-core"))
            api(libs.ktor.server.validation)
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
        }
        commonTest.dependencies {
            implementation(libs.mockk)
            implementation(libs.ktor.server.test)
            implementation("io.ktor:ktor-server-content-negotiation:2.3.12")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12")
        }
    }
}
