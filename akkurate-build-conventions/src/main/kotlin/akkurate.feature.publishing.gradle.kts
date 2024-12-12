plugins {
    `maven-publish`
    signing
}

// Create a Javadoc JAR
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier = "javadoc"
}

ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
ext["sonatypeUsername"] = System.getenv("SONATYPE_USERNAME")
ext["sonatypePassword"] = System.getenv("SONATYPE_PASSWORD")

fun hasExtra(name: String) = ext[name] != null
fun getExtra(name: String) = ext[name]?.toString()

publishing {
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

    publications.withType<MavenPublication>().configureEach {
        artifact(javadocJar.get())

        pom {
            name = "Akkurate"
            description = "The expressive validation library for Kotlin"
            url = "https://akkurate.dev"

            licenses {
                license {
                    name = "Apache License 2.0"
                    url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                }
            }
            developers {
                developer {
                    id = "nesk"
                    name = "Johann Pardanaud"
                    email = "johann@pardanaud.com"
                }
            }
            scm {
                url = "https://github.com/nesk/akkurate"
            }
        }
    }
}

if (hasExtra("signing.keyId") && hasExtra("signing.password") && hasExtra("signing.secretKeyRingFile")) {
    signing {
        sign(publishing.publications)
    }
}

tasks.assemble { dependsOn(javadocJar) }

// Fix Gradle warning about signing tasks using publishing task outputs without explicit dependencies
// https://youtrack.jetbrains.com/issue/KT-46466#focus=Comments-27-6349423.0-0
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach { mustRunAfter(signingTasks) }