pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "akkurate"

include("library", "plugin", "examples:walkthrough")
project(":library").name = "akkurate-core"
project(":plugin").name = "akkurate-ksp-plugin"
