plugins {
    id("akkurate.component.kmp-library")
    id("akkurate.feature.publishing")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization")
}

component {
    ignoredTargets { wasmWasi() }
}

kotlin {
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
        }
        jvmTest.dependencies {
            implementation(libs.mockk)
        }
    }
}
