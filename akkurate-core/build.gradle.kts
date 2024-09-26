import dev.nesk.akkurate.gradle.configureTargets
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("akkurate.kmp-library-conventions")
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("org.jetbrains.dokka")
}


kotlin {
    configureTargets()

    sourceSets {
        commonMain {
            // Make the common source set depend on the generated validatable accessors, to make them accessible to all targets.
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
                implementation(project(":akkurate-test"))
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", project(":akkurate-ksp-plugin"))
}

ksp {
    arg("__PRIVATE_API__validatablePackages", "kotlin|kotlin.collections")
    arg("__PRIVATE_API__prependPackagesWith", "dev.nesk.akkurate.accessors")
    arg("appendPackagesWith", "")
}

// Make Gradle aware of the dependency on the generated validatable accessors.
tasks {
    withType<KotlinCompilationTask<*>>().configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }

    withType<Jar>().configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
