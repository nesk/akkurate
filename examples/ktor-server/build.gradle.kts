plugins {
    id("akkurate.base-conventions")
    kotlin("jvm")
    id("io.ktor.plugin")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.server.hostCommon)
    implementation(libs.ktor.server.statusPages)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback)
    testImplementation(libs.ktor.server.test)
    testImplementation(libs.kotlin.test.junit)

    implementation(project(":akkurate-core"))
    implementation(project(":akkurate-ksp-plugin"))
    implementation(project(":akkurate-ktor-server"))
    ksp(project(":akkurate-ksp-plugin"))
    implementation(libs.ktor.server.requestValidation)
}

kotlin {
    jvmToolchain(8)
}
