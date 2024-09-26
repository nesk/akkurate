import dev.nesk.akkurate.gradle.configurePom

plugins {
    id("akkurate.publishing-conventions")
    kotlin("jvm")
    id("org.jetbrains.dokka")
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

dependencies {
    implementation("com.squareup:kotlinpoet:1.14.2")
    implementation("com.squareup:kotlinpoet-ksp:1.14.2")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.10-1.0.13")

    testImplementation(kotlin("test"))
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")
}

kotlin {
    explicitApi()
    jvmToolchain(8)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing.publications.create<MavenPublication>("release") {
    from(components["kotlin"])
    artifact(tasks.named("javadocJar").get())
    artifact(tasks.named("sourcesJar").get())
    configurePom()
}
