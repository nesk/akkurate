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
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}
