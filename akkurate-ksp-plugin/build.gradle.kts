import dev.nesk.akkurate.gradle.configurePom

plugins {
    id("akkurate.publishing-conventions")
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("com.karumi.kotlin-snapshot")
}

dependencies {
    implementation(libs.kotlinpoet.core)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.kotlin.symbolProcessing.api)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinCompileTesting.ksp)
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
