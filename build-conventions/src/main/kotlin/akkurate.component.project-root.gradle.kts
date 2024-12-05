import dev.nesk.akkurate.gradle.catalogVersion

plugins {
    id("akkurate.base.repositories")
}

tasks.named<Wrapper>("wrapper") {
    val expectedGradleVersion = project.catalogVersion(versionRef = "gradle-wrapper")
    val actualGradleVersion = project.gradle.gradleVersion
    validateDistributionUrl = expectedGradleVersion != actualGradleVersion
    gradleVersion = expectedGradleVersion
}
