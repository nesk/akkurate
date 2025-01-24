import dev.nesk.akkurate.gradle.IgnoredTarget
import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization")
}

kotlin {
    configureTargets(IgnoredTarget.WASM_WASI)

    sourceSets {
        commonMain.dependencies {
            api(project(":akkurate-core"))
            api(libs.ktor.server.requestValidation)
            implementation(libs.kotlinx.serialization.core)
        }
        commonTest.dependencies {
            implementation(libs.ktor.server.test)
            implementation(libs.ktor.server.contentNegotiation)
            implementation(libs.ktor.serialization.json)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            implementation(libs.mockk)
        }
    }
}
