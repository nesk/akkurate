pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "akkurate"

include("library", "plugin", "akkurate-test", "integrations:arrow", "integrations:kotlinx-datetime", "examples:ktor-server")
project(":library").name = "akkurate-core"
project(":plugin").name = "akkurate-ksp-plugin"
project(":integrations:arrow").name = "akkurate-arrow"
project(":integrations:kotlinx-datetime").name = "akkurate-kotlinx-datetime"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("arrow-core", "io.arrow-kt:arrow-core:1.2.0")
            library("kotlinx-datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
        }
    }
}
