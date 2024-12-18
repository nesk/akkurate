plugins {
    id("akkurate.component.kmp-library")
    id("akkurate.feature.publishing")
    id("org.jetbrains.dokka")
}

component {
    ignoredTargets {
        wasmJs()
        wasmWasi()
        watchosDeviceArm64()
        androidNative()
    }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":akkurate-core"))
                api(libs.arrow.core)
            }
        }
    }
}
