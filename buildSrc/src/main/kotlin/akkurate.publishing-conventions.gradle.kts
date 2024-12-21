plugins {
    id("akkurate.base-conventions")
    `maven-publish`
    signing
}

publishing {
    // Configure maven central repository
    repositories {
        maven {
            name = "sonatype"

            System.getenv("SONATYPE_URL")?.let { setUrl(it) }

            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

// Signing artifacts
signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)

    if (signingKey !== null && signingPassword !== null) {
        sign(publishing.publications)
    }
}
