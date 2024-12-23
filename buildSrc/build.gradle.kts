plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

dependencies {
    implementation(libs.kotlin.plugin)
    implementation(libs.kotlin.symbolProcessing.plugin)
    implementation(libs.kotlinx.serialization.plugin)
    implementation(libs.kotlinSnapshot.plugin)
    implementation(libs.dokka.plugin)
    implementation(libs.ktor.plugin)
    implementation(libs.testLogger.plugin)
    implementation(libs.kotlinx.binaryValidator.plugin)
}
