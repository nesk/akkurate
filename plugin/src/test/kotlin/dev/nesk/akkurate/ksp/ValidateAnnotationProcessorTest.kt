/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nesk.akkurate.ksp

import com.karumi.kotlinsnapshot.matchWithSnapshot
import com.tschuchort.compiletesting.*
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
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process a single annotated class")
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
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process multiple annotated classes")
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
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes referenced as nullables")
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
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes with cyclic references")
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
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes with cyclic nullable references")
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
        Path("${compiler.kotlinFilesDir}/dev/nesk/company/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes across multiple packages (company)")
        Path("${compiler.kotlinFilesDir}/dev/nesk/user/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("can process annotated classes across multiple packages (user)")
    }

    @Test
    fun `the option 'appendPackagesWith' appends its value to the original package name`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val name: String)
            """
        )

        // Act
        val (result, compiler) = compile(source, options = mapOf("appendPackagesWith" to " ..foo.bar.. "))

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/foo/bar/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'appendPackagesWith' appends its value to the original package name")
    }

    @Test
    fun `the option 'validatableClasses' processes additional classes and interfaces`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val name: String)
            """
        )

        // Act
        val (result, compiler) = compile(
            source,
            options = mapOf("validatableClasses" to "${String::class.qualifiedName!!}|${CharSequence::class.qualifiedName!!}")
        )

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(2, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'validatableClasses' processes additional classes and interfaces (dev.nesk)")
        Path("${compiler.kotlinFilesDir}/kotlin/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'validatableClasses' processes additional classes and interfaces (kotlin)")
    }

    @Test
    fun `the option 'overrideOriginalPackageWith' overrides the original package of the symbols with its value`() {
        // Arrange
        val source = SourceFile.kotlin("Examples.kt", "")

        // Act
        val (result, compiler) = compile(
            source, options = mapOf(
                "__PRIVATE_API__overrideOriginalPackageWith" to "dev.nesk",
                "validatableClasses" to String::class.qualifiedName!!,
            )
        )

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'overrideOriginalPackageWith' overrides the original package of the symbols with its value")
    }

    private fun compile(vararg sources: SourceFile, options: Map<String, String> = emptyMap()): Pair<KotlinCompilation.Result, KotlinCompilation> {
        val compiler = KotlinCompilation().apply {
            inheritClassPath = true
            symbolProcessorProviders = listOf(ValidateAnnotationProcessorProvider())
            kspArgs = options.toMutableMap()
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
