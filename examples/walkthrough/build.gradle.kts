plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}

group = "me.johann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":plugin"))
    implementation(project(":library"))
    ksp(project(":plugin"))
}

kotlin {
    jvmToolchain(11)
}
