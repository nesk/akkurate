plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
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
