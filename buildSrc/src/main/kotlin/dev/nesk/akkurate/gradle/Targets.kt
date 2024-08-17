package dev.nesk.akkurate.gradle

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Configures all the available targets on a project, with the ability to ignore some of them.
 *
 * Some projects can't support all the targets, due to their dependencies. This function enables
 * easy target configuration from a project to another. By default, all the targets are added to
 * the project, and the conflicting ones can be ignored.
 *
 * @param ignoredTargets The targets to ignore.
 */
fun KotlinMultiplatformExtension.configureTargets(vararg ignoredTargets: IgnoredTarget) {
    jvm()
}

enum class IgnoredTarget
