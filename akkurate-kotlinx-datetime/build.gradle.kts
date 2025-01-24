import dev.nesk.akkurate.gradle.IgnoredTarget
import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
}

kotlin {
    configureTargets(IgnoredTarget.WASM_WASI)

    sourceSets {
        commonMain.dependencies {
            api(project(":akkurate-core"))
            api(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(project(":akkurate-test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
