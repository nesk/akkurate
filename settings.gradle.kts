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

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("arrow-core", "io.arrow-kt:arrow-core:1.2.0")
            library("kotlinx-datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            library("ktor-server-validation", "io.ktor:ktor-server-request-validation:2.3.12")
            library("ktor-server-test", "io.ktor:ktor-server-test-host:2.3.12")
            library("ktor-client-core", "io.ktor:ktor-client-core:2.3.12")
            library("mockk", "io.mockk:mockk:1.13.13")
        }
    }
}
