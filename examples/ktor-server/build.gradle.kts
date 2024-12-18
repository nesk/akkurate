plugins {
    id("akkurate.component.kotlin-application")
    id("akkurate.feature.ksp")
    id("io.ktor.plugin")
    kotlin("plugin.serialization")
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)
    implementation(libs.logback)
    testImplementation(libs.ktor.server.test)
    testImplementation(libs.kotlin.test.junit)

    implementation(project(":akkurate-core"))
    implementation(project(":akkurate-ksp-plugin"))
    implementation(project(":akkurate-ktor-server"))
    ksp(project(":akkurate-ksp-plugin"))
}
