plugins {
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

tasks.named<Wrapper>("wrapper") {
    gradleVersion = libs.versions.gradle.wrapper.get()
}