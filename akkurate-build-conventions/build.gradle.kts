import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    `kotlin-dsl`
}

group = "dev.nesk.akkurate"
version = "1.0"

kotlin {
    // see https://docs.gradle.org/current/userguide/compatibility.html
    coreLibrariesVersion = "2.0.20"
}

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompilationTask<KotlinJvmCompilerOptions>>().configureEach {
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_8
        jvmTarget = JvmTarget.JVM_1_8
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Workaround to use type-safe version catalog accessors in convention plugins,
    // see https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.kotlin.plugin)
    implementation(libs.kotlin.symbolProcessing.plugin)
    implementation(libs.dokka.plugin)
    implementation(libs.kotlinSnapshot.plugin)
    implementation(libs.ktor.plugin)
    implementation(libs.kotlin.serialization.plugin)
    implementation(libs.testLogger.plugin)
}