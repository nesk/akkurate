import dev.nesk.akkurate.gradle.IgnoredTarget
import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
}

kotlin {
    configureTargets(IgnoredTarget.JS_IR, IgnoredTarget.WASM_JS, IgnoredTarget.WASM_WASI)

    sourceSets {
        commonMain.dependencies {
            api(project(":akkurate-core"))
            api(libs.ktor.client.core)
        }
    }
}
