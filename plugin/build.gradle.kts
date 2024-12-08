plugins {
    id("akkurate.component.ksp-processor")
    id("akkurate.feature.publishing")
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

java {
    withSourcesJar()
    withJavadocJar()
}

publishing.publications.create<MavenPublication>("release") {
    from(components["kotlin"])
    artifact(tasks.named("javadocJar").get())
    artifact(tasks.named("sourcesJar").get())
}
