import dev.nesk.akkurate.gradle.IgnoredTarget
import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    configureTargets(IgnoredTarget.WASM_WASI)

    sourceSets {
        commonMain.dependencies {
            api(project(":akkurate-core"))
            api(libs.ktor.client.core)
        }
        commonTest.dependencies {
            implementation(libs.ktor.server.test)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.json)
        }
    }
}
