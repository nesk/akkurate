import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`
}

group = "dev.nesk.akkurate"
version = "1.0"

val targetJavaVersion = libs.versions.java.target.build.map(JavaVersion::toVersion)
val targetKotlinApiVersion = libs.versions.kotlin.target.api.build.map(KotlinVersion::fromVersion)
val targetKotlinLibsVersion = libs.versions.kotlin.target.libs.build

kotlin {
    coreLibrariesVersion = targetKotlinLibsVersion.get()

    compilerOptions {
        languageVersion = targetKotlinApiVersion
        apiVersion = targetKotlinApiVersion
        jvmTarget = targetJavaVersion.map { JvmTarget.fromTarget(it.toString()) }
    }
}

java {
    val version = targetJavaVersion.get()
    sourceCompatibility = version
    targetCompatibility = version
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Workaround to use type-safe version catalog accessors in convention plugins,
    // see https://github.com/gradle/gradle/issues/15383 for more info
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.kotlin.plugin)
    implementation(libs.kotlin.symbolProcessing.plugin)
    implementation(libs.kotlinx.serialization.plugin)
    implementation(libs.kotlinSnapshot.plugin)
    implementation(libs.dokka.plugin)
    implementation(libs.ktor.plugin)
    implementation(libs.testLogger.plugin)
}