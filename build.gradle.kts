plugins {
    id("org.jetbrains.dokka")
}

repositories {
    mavenCentral()
}

/*
 * Configure default gradle wrapper task to validate currently used gradle wrapper version against value set in versions
 * catalog. When value differs task will automatically update wrapper configuration file with version from catalog.
 * After this gradle wrapper binary will be updated before next task execution.
*/
tasks.named<Wrapper>("wrapper") {
    gradleVersion = libs.versions.gradle.wrapper.get()
}