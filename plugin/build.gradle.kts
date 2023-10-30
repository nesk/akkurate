plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
    id("com.adarshr.test-logger") version "3.2.0"
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

group = "dev.nesk.akkurate"
version = "0.4.0"

repositories {
    mavenCentral()
}

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
    publications.create<MavenPublication>("release") {
        from(components["kotlin"])
        artifact(tasks.named("javadocJar").get())
        artifact(tasks.named("sourcesJar").get())

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
