plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

dependencies {
    implementation("com.adarshr:gradle-test-logger-plugin:3.2.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
}
