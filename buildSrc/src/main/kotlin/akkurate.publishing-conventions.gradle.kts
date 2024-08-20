plugins {
    id("akkurate.base-conventions")
    `maven-publish`
    signing
}

ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
ext["sonatypeUsername"] = System.getenv("SONATYPE_USERNAME")
ext["sonatypePassword"] = System.getenv("SONATYPE_PASSWORD")

fun hasExtra(name: String) = ext[name] != null
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
}

// Signing artifacts. Signing.* extra properties values will be used
if (hasExtra("signing.keyId") &&
    hasExtra("signing.password") &&
    hasExtra("signing.secretKeyRingFile")
) {
    signing {
        sign(publishing.publications)
    }
}
