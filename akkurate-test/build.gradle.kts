plugins {
    id("akkurate.component.kmp-library")
    id("akkurate.feature.publishing")
    id("org.jetbrains.dokka")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":akkurate-core"))
            }
        }
    }
}
