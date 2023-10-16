plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
    id("com.adarshr.test-logger") version "3.2.0"
}

group = "dev.nesk.akkurate"
version = "0.3.0"

repositories {
    mavenCentral()
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

dependencies {
    add("kspCommonMainMetadata", project(":akkurate-ksp-plugin"))
    add("kspJvm", project(":akkurate-ksp-plugin"))
}

ksp {
    arg("__PRIVATE_API__validatablePackages", "kotlin|kotlin.collections")
    arg("__PRIVATE_API__prependPackagesWith", "dev.nesk.akkurate.accessors")
    arg("appendPackagesWith", "")
}

//region Create a Javadoc JAR
val javadocJar by tasks.registering(Jar::class) { archiveClassifier.set("javadoc") }
tasks.assemble { dependsOn(javadocJar) }
//endregion

//region Fix Gradle warning about signing tasks using publishing task outputs without explicit dependencies
// https://youtrack.jetbrains.com/issue/KT-46466#focus=Comments-27-6349423.0-0
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach { mustRunAfter(signingTasks) }
//endregion

//region Publication & signing
ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
ext["sonatypeUsername"] = System.getenv("SONATYPE_USERNAME")
ext["sonatypePassword"] = System.getenv("SONATYPE_PASSWORD")

fun getExtra(name: String) = ext[name]?.toString()

publishing {
    // Configure maven central repository
    repositories {
        maven {
            name = "sonatype"
            credentials {
                username = getExtra("sonatypeUsername")
                password = getExtra("sonatypePassword")
            }

            val isSnapshot = project.version.toString().endsWith("-SNAPSHOT")
            if (isSnapshot) {
                setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            } else {
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }

    // Configure all publications
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        // Provide artifacts information requited by Maven Central
        pom {
            name.set("Akkurate")
            description.set("An expressive validation library for Kotlin")
            url.set("https://akkurate.dev")

            licenses {
                license {
                    name.set("Apache License 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("nesk")
                    name.set("Johann Pardanaud")
                    email.set("johann@pardanaud.com")
                }
            }
            scm {
                url.set("https://github.com/nesk/akkurate")
            }
        }
    }
}

// Signing artifacts. Signing.* extra properties values will be used
signing {
    sign(publishing.publications)
}
//endregion
