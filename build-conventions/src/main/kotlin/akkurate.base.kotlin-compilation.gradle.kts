import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

extensions.configure<KotlinTopLevelExtension> {
    jvmToolchain(8)
}