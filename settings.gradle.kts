pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "akkurate"

include("akkurate-core", "akkurate-ksp-plugin", "akkurate-test", "akkurate-arrow", "akkurate-kotlinx-datetime", "examples:ktor-server")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("arrow-core", "io.arrow-kt:arrow-core:1.2.0")
            library("kotlinx-datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
        }
    }
}
