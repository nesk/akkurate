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
import dev.nesk.akkurate.annotations.ExperimentalAkkurateCompilerApi
import kotlin.io.path.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalPathApi::class)
class ValidateAnnotationProcessorTest {
    @Test
    fun `processing without annotated classes doesn't generate any file`() {
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
    fun `processing a single annotated class generates a single file containing the accessors`() {
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
            .matchWithSnapshot("processing a single annotated class generates a single file containing the accessors")
    }

    @Test
    fun `processing multiple annotated classes in the same package generates a single file containing the accessors`() {
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
            .matchWithSnapshot("processing multiple annotated classes in the same package generates a single file containing the accessors")
    }

    @Test
    fun `processing a documented class generates accessors with the same documentation`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(
                    /**
                     * The first name of the user. Must not be empty or blank. 
                     */
                    val firstName: String,
                    /**
                     * The last name of the user. Must not be empty or blank. 
                     */
                    val lastName: String,
                ) {
                    /**
                     * The full name of the user, a simple concatenation of [firstName] and [lastName].
                     *
                     * Example:
                     * 
                     * ```
                     * val user = User("John", "Doe")
                     * user.fullName // "John Doe"
                     * ```
                     */
                    val fullName: String get() = "${'$'}firstName ${'$'}lastName"
                }
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("processing a documented class generates accessors with the same documentation")
    }

    @Test
    fun `processing a generic class generates generic accessors`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class Wrapper<A, out B, C : CharSequence>(
                    val prop1: A,
                    val prop2: List<A>,
                    val prop3: List<Map<B, C>>,
                    val prop4: List<String>,
                )
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("processing a generic class generates generic accessors")
    }

    @Test
    fun `processing annotated classes referenced as nullables generates nullable accessors`() {
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
            .matchWithSnapshot("processing annotated classes referenced as nullables generates nullable accessors")
    }

    @Test
    fun `processing annotated classes with cyclic references generates accessors for all the classes without failing`() {
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
            .matchWithSnapshot("processing annotated classes with cyclic references generates accessors for all the classes without failing")
    }

    @Test
    fun `processing annotated classes with cyclic nullable references generates nullable accessors for all the classes without failing`() {
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
            .matchWithSnapshot("processing annotated classes with cyclic nullable references generates nullable accessors for all the classes without failing")
    }

    @Test
    fun `processing annotated classes across multiple packages generates a file per package`() {
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
            .matchWithSnapshot("processing annotated classes across multiple packages generates a file per package (company)")
        Path("${compiler.kotlinFilesDir}/dev/nesk/user/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("processing annotated classes across multiple packages generates a file per package (user)")
    }

    @Test
    fun `accessors for mutable properties are casted to the right type`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(var name: String)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("accessors for mutable properties are casted to the right type")
    }

    @Test
    fun `accessors are generated only for public properties`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                private class PrivateParent {
                    public class PublicParent {
                        @Validate public class PublicClass(public val publicPropInPrivateScope: Any)
                    }
                }
                @Validate public class PublicClass(public val publicPropInPublicScope: Any, private val privatePropInPublicScope: Any)
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("accessors are generated only for public properties")
    }

    @Test
    fun `accessors are generated for nested classes`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class Foo {
                    class Bar(val baz: Any)                
                }
            """
        )

        // Act
        val (result, compiler) = compile(source)

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("accessors are generated for nested classes")
    }

    @Test
    fun `accessors within the 'kotlin' package are ignored and not written to the generated files`() {
        // Act
        val (result, compiler) = compile(options = mapOf("validatableClasses" to String::class.qualifiedName!!))

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(0, compiler)
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
    fun `the option 'prependPackagesWith' prepends its value to the original package name`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val name: String)
            """
        )

        // Act
        val (result, compiler) = compile(source, options = mapOf("__PRIVATE_API__prependPackagesWith" to " ..foo.bar.. "))

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/foo/bar/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'prependPackagesWith' prepends its value to the original package name")
    }

    @Test
    fun `the option 'validatableClasses' processes additional classes and interfaces`() {
        // Arrange
        val source = SourceFile.kotlin(
            "Examples.kt", """
                package dev.nesk
                import dev.nesk.akkurate.annotations.Validate
                @Validate class User(val firstName: FirstName, val lastName: LastName)

                class FirstName(val text: String)
                class LastName(val text: String)
            """
        )

        // Act
        val (result, compiler) = compile(
            source,
            options = mapOf("validatableClasses" to "dev.nesk.FirstName|dev.nesk.LastName")
        )

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(1, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'validatableClasses' processes additional classes and interfaces")
    }

    @Test
    fun `the option 'validatablePackages' processes all the classes of the provided packages`() {
        // Arrange
        val companySource = SourceFile.kotlin(
            "Company.kt", """
                package dev.nesk.company
                import dev.nesk.user.User
                class Company(val admin: User)
            """
        )
        val userSource = SourceFile.kotlin(
            "User.kt", """
                package dev.nesk.user
                import dev.nesk.company.Company
                class User(val company: Company)
            """
        )

        // Act
        val (result, compiler) = compile(
            companySource,
            userSource,
            options = mapOf("__PRIVATE_API__validatablePackages" to "dev.nesk.company|dev.nesk.user")
        )

        // Assert
        assertCompilationIsSuccessful(result)
        assertCountOfFilesGeneratedByTheProcessor(2, compiler)
        Path("${compiler.kotlinFilesDir}/dev/nesk/company/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'validatablePackages' processes all the classes of the provided packages (dev.nesk.company)")
        Path("${compiler.kotlinFilesDir}/dev/nesk/user/validation/accessors/ValidationAccessors.kt").readText()
            .matchWithSnapshot("the option 'validatablePackages' processes all the classes of the provided packages (dev.nesk.user)")
    }

    @OptIn(ExperimentalAkkurateCompilerApi::class)
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
