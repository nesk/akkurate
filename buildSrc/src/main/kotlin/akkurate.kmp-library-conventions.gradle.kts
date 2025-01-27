import dev.nesk.akkurate.gradle.configurePom

plugins {
    id("akkurate.publishing-conventions")
    kotlin("multiplatform")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

kotlin {
    explicitApi()
    jvmToolchain(8)

    sourceSets {
        all {
            languageSettings {
                // See: https://kotlinlang.org/docs/multiplatform-dsl-reference.html#language-settings
                languageVersion = "1.9"
                apiVersion = "1.9"
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

// Binary compatibility
apiValidation {
    @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
    klib {
        enabled = true
    }
}

// Create a Javadoc JAR
val javadocJar by tasks.registering(Jar::class) { archiveClassifier.set("javadoc") }
tasks.assemble { dependsOn(javadocJar) }

// Fix Gradle warning about signing tasks using publishing task outputs without explicit dependencies
// https://youtrack.jetbrains.com/issue/KT-46466#focus=Comments-27-6349423.0-0
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach { mustRunAfter(signingTasks) }

publishing.publications.withType<MavenPublication> {
    artifact(javadocJar.get())
    configurePom()
}
