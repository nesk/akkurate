plugins {
    id("akkurate.base.repositories")
}

tasks.named<Wrapper>("wrapper") {
    val requiredGradleVersion = project.extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
        .findVersion("gradle-wrapper")
        .get()
        .requiredVersion
    val currentGradleVersion = project.gradle.gradleVersion
    validateDistributionUrl = requiredGradleVersion != currentGradleVersion
    gradleVersion = requiredGradleVersion
}
