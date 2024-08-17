import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("org.jetbrains.dokka")
}

kotlin {
    configureTargets()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":akkurate-core"))
                api(libs.arrow.core)
            }
        }
    }
}
