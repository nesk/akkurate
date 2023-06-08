plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "me.johann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":library"))
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("dev.nesk.akkurate.examples.full.MainKt")
}
