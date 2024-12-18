import dev.nesk.akkurate.gradle.libs

plugins {
    id("akkurate.base.repositories")
}

tasks.named<Wrapper>("wrapper") {
    val expectedGradleVersion = libs.versions.gradle.wrapper.get()
    val actualGradleVersion = project.gradle.gradleVersion
    validateDistributionUrl = expectedGradleVersion != actualGradleVersion
    gradleVersion = expectedGradleVersion
}
