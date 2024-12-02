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
    "examples:ktor-server",
)

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("ktor", "3.0.0")
            version("kotlinx-serialization", "1.7.3")

            plugin("kotlinx-serialization", "org.jetbrains.kotlin.plugin.serialization").version("2.0.20")

            library("arrow-core", "io.arrow-kt:arrow-core:1.2.0")
            library("kotlinx-datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            library("kotlinx-serialization-core", "org.jetbrains.kotlinx", "kotlinx-serialization-core").versionRef("kotlinx-serialization")
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlinx-serialization")
            library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            library("ktor-server-contentNegotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-server-requestValidation", "io.ktor", "ktor-server-request-validation").versionRef("ktor")
            library("ktor-server-test", "io.ktor", "ktor-server-test-host").versionRef("ktor")
            library("mockk", "io.mockk:mockk:1.13.13")
        }
    }
}
