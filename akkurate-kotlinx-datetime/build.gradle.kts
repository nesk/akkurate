plugins {
    id("akkurate.component.kmp-library")
    id("akkurate.feature.publishing")
    id("org.jetbrains.dokka")
}

kmpLibrary {
    ignoreTargets { wasmWasi() }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":akkurate-core"))
                api(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(project(":akkurate-test"))
            }
        }
    }
}
