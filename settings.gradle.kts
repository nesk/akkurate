pluginManagement {
    includeBuild("build-conventions") {
       name = "akkurate-build-conventions"
    }
}

rootProject.name = "akkurate"

include("library", "plugin", "akkurate-test", "integrations:arrow", "integrations:kotlinx-datetime", "examples:ktor-server")
project(":library").name = "akkurate-core"
project(":plugin").name = "akkurate-ksp-plugin"
project(":integrations:arrow").name = "akkurate-arrow"
project(":integrations:kotlinx-datetime").name = "akkurate-kotlinx-datetime"
