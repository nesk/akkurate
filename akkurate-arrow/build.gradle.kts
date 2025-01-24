import dev.nesk.akkurate.gradle.IgnoredTarget
import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
}

kotlin {
    configureTargets(
        IgnoredTarget.ANDROID_NATIVE,
        IgnoredTarget.WASM_JS,
        IgnoredTarget.WASM_WASI,
        IgnoredTarget.WATCHOS_DEVICE_ARM64
    )

    sourceSets {
        commonMain {
            dependencies {
                api(project(":akkurate-core"))
                api(libs.arrow.core)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}
