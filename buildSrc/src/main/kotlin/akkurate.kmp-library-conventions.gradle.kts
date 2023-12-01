import dev.nesk.akkurate.gradle.configurePom

plugins {
    id("akkurate.publishing-conventions")
    kotlin("multiplatform")
}

kotlin {
    explicitApi()

    jvm {
        jvmToolchain(8)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            }
        }
        val jvmMain by getting
        val jvmTest by getting
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
