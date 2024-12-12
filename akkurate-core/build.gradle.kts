import dev.nesk.akkurate.gradle.kspKotlinOutputDir
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("akkurate.component.kmp-library")
    id("akkurate.feature.ksp")
    id("akkurate.feature.publishing")
    id("org.jetbrains.dokka")
}

kotlin {
    sourceSets {
        commonMain {
            // Make the common source set depend on the generated validatable accessors, to make them accessible to all targets.
            kotlin.srcDir(kspKotlinOutputDir("metadata"))
        }
        commonTest.dependencies {
            implementation(project(":akkurate-test"))
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
