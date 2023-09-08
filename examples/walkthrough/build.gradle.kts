plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}

group = "dev.nesk.akkurate"
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
