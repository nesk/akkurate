pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "akkurate"

include("library", "plugin", "integrations:arrow", "examples:ktor-server")
project(":library").name = "akkurate-core"
project(":plugin").name = "akkurate-ksp-plugin"
project(":integrations:arrow").name = "akkurate-arrow"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("arrow-core", "io.arrow-kt:arrow-core:1.2.0")
        }
    }
}
