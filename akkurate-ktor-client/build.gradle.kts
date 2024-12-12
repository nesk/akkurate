plugins {
    id("akkurate.component.kmp-library")
    id("akkurate.feature.publishing")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization")
}

kmpLibrary {
    ignoreTargets { wasmWasi() }
}

kotlin {
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
