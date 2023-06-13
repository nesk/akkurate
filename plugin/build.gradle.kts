plugins {
    kotlin("jvm")
}

group = "me.johann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.14.2")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.21-1.0.11")
    implementation(project(":library"))

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}
