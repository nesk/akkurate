pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

rootProject.name = "akkurate"

include(
    "akkurate-core",
    "akkurate-ksp-plugin",
    "akkurate-test",
    "akkurate-arrow",
    "akkurate-kotlinx-datetime",
    "akkurate-ktor-server",
    "akkurate-ktor-client",
    "examples:ktor-server",
)