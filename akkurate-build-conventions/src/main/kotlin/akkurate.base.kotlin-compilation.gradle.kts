import dev.nesk.akkurate.gradle.libs
import dev.nesk.akkurate.gradle.toJvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLibsTargetVersion = libs.versions.kotlin.target.libs.asProvider()
val kotlinApiTargetVersion = libs.versions.kotlin.target.api.asProvider().map(KotlinVersion::fromVersion)
val javaTargetVersion = libs.versions.java.target.asProvider().map(JavaVersion::toVersion)

extensions.configure<KotlinProjectExtension> {
    coreLibrariesVersion = kotlinLibsTargetVersion.get()
    sourceSets.all {
        languageSettings {
            // See https://kotlinlang.org/docs/multiplatform-dsl-reference.html#language-settings
            val version = kotlinApiTargetVersion.get().version
            languageVersion = version
            apiVersion = version
        }
    }
}

extensions.configure<JavaPluginExtension> {
    val version = javaTargetVersion.get()
    sourceCompatibility = version
    targetCompatibility = version
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = javaTargetVersion.map(JavaVersion::toJvmTarget)
        freeCompilerArgs.add(
            javaTargetVersion.map {
                // See https://kotlinlang.org/docs/compiler-reference.html#xjdk-release-version
                "-Xjdk-release=$it"
            }
        )
    }
}