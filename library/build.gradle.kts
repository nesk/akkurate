import dev.nesk.akkurate.gradle.configureTargets

plugins {
    id("akkurate.kmp-library-conventions")
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("org.jetbrains.dokka")
}


kotlin {
    configureTargets()
    
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            }
        }
    }
}

ksp {
    arg("__PRIVATE_API__validatablePackages", "kotlin|kotlin.collections")
    arg("__PRIVATE_API__prependPackagesWith", "dev.nesk.akkurate.accessors")
    arg("appendPackagesWith", "")
}
