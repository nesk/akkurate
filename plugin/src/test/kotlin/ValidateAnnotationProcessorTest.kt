import com.karumi.kotlinsnapshot.matchWithSnapshot
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import kotlin.io.path.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalPathApi::class)
class ValidateAnnotationProcessorTest {
    @Test
    fun `can process without annotations`() {
        // Arrange
        val source = SourceFile.kotlin("Examples.kt", "class User(val name: String)")
        // Act
        val (result, compiler) = compile(source)
        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(0, compiler)
        assertTrue(Path(compiler.kotlinFilesDir).listDirectoryEntries().isEmpty())
    }

    @Test
    fun `can process a single annotated class`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val name: String)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/ValidationAccessors.kt").readText().matchWithSnapshot()
    }

    @Test
    fun `can process multiple annotated classes`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val name: UserName)
                @Validate class UserName(val text: String)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/ValidationAccessors.kt").readText().matchWithSnapshot()
    }

    @Test
    fun `can process annotated classes referenced as nullables`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val name: UserName?)
                @Validate class UserName(val text: String)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/ValidationAccessors.kt").readText().matchWithSnapshot()
    }

    @Test
    fun `can process annotated classes with cyclic references`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class Company(val admin: User)
                @Validate class User(val company: Company)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/ValidationAccessors.kt").readText().matchWithSnapshot()
    }

    @Test
    fun `can process annotated classes with cyclic nullable references`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class Company(val admin: User?)
                @Validate class User(val company: Company?)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/ValidationAccessors.kt").readText().matchWithSnapshot()
    }

    @Test
    fun `can process annotated classes across multiple packages`() {
        // Arrange
        val companySource = SourceFile.kotlin(
            "Company.kt", """
                package dev.nesk.company
                import dev.nesk.akkurate.annotations.Validate
                import dev.nesk.user.User
                @Validate class Company(val admin: User)
            """
        )
        val userSource = SourceFile.kotlin(
            "User.kt", """
                package dev.nesk.user
                import dev.nesk.akkurate.annotations.Validate
                import dev.nesk.company.Company
                @Validate class User(val company: Company)
            """
        )

        // Act
        val (result, compiler) = compile(companySource, userSource)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(2, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/company/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes across multiple packages (company)")
        Path("${compiler.kotlinFilesDir}/dev/nesk/user/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes across multiple packages (user)")
    }

    private fun compile(vararg sources: SourceFile): Pair<KotlinCompilation.Result, KotlinCompilation> {
        val compiler = KotlinCompilation().apply {
            inheritClassPath = true
            symbolProcessorProviders = listOf(ValidateAnnotationProcessorProvider())
            this.sources = listOf(*sources)
        }
        val result = compiler.compile()
        return result to compiler
    }

    private val KotlinCompilation.kotlinFilesDir: String get() = "${kspSourcesDir.absolutePath}/kotlin"

    private fun assertCompilationIsSuccessful(result: KotlinCompilation.Result) {
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode, "Compilation is successful")
    }

    private fun assertCountOfFilesGeneratedByTheProcessor(expected: Int, compiler: KotlinCompilation) {
        assertEquals(expected, Path(compiler.kotlinFilesDir).walk().count(), "Count the files generated by the processor")
    }
}
