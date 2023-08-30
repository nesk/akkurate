plugins {
    kotlin("jvm")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.karumi.kotlinsnapshot:plugin:2.3.0")
    }
}
apply(plugin = "com.karumi.kotlin-snapshot")

group = "me.johann"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup:kotlinpoet:1.14.2")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.21-1.0.11")

    testImplementation(kotlin("test"))
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")
}

kotlin {
    jvmToolchain(11)
}

tasks.test {
    useJUnitPlatform()
}
