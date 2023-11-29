package dev.nesk.akkurate.gradle

import org.gradle.api.publish.maven.MavenPublication

/**
 * Configures artifact information requiter by Maven Central.
 */
fun MavenPublication.configurePom() {
    pom {
        name.set("Akkurate")
        description.set("The expressive validation library for Kotlin")
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
