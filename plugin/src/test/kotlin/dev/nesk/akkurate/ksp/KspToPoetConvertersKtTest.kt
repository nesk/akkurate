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

import com.google.devtools.ksp.symbol.*
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KspToPoetConvertersKtTest {
    @Test
    fun `can convert a KSP declaration to a Poet class name`() {
        // Arrange
        val kspDeclaration = KSDeclarationFake(
            "packageName" to KSNameFake("com.example"),
            "simpleName" to KSNameFake("Media"),
            "parentDeclaration" to null,
        )

        // Act
        val poetClassName = kspDeclaration.toClassName()

        // Assert
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
    }

    @Test
    fun `can convert a nested KSP declaration to a Poet class name`() {
        // Arrange
        val kspDeclaration = KSDeclarationFake(
            "packageName" to KSNameFake("com.example"),
            "simpleName" to KSNameFake("Png"),
            "parentDeclaration" to KSDeclarationFake(
                "packageName" to KSNameFake("com.example"),
                "simpleName" to KSNameFake("Image"),
                "parentDeclaration" to KSDeclarationFake(
                    "packageName" to KSNameFake("com.example"),
                    "simpleName" to KSNameFake("Media"),
                    "parentDeclaration" to null,
                )
            )
        )

        // Act
        val poetClassName = kspDeclaration.toClassName()

        // Assert
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Png", poetClassName.simpleName)
        assertEquals(listOf("Media", "Image", "Png"), poetClassName.simpleNames)
    }

    @Test
    fun `can convert a KSP declaration to a Poet type name`() {
        // Arrange
        val kspDeclaration = KSDeclarationFake(
            "packageName" to KSNameFake("com.example"),
            "simpleName" to KSNameFake("Media"),
            "parentDeclaration" to null,
        )

        // Act
        val poetTypeName = kspDeclaration.toTypeName()

        // Assert
        val poetClassName = poetTypeName as ClassName
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
    }

    @Test
    fun `can convert a KSP type to a Poet type name`() {
        // Arrange
        val kspType = KSTypeFake(
            "declaration" to KSDeclarationFake(
                "packageName" to KSNameFake("com.example"),
                "simpleName" to KSNameFake("Media"),
                "parentDeclaration" to null,
            ),
            "arguments" to emptyList<KSTypeArgument>(),
            "nullability" to Nullability.NOT_NULL,
        )

        // Act
        val poetTypeName = kspType.toTypeName()

        // Assert
        val poetClassName = poetTypeName as ClassName
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
        assertFalse(poetClassName.isNullable)
    }

    @Test
    fun `can convert a generic KSP type to a Poet type name`() {
        // Arrange
        val kspType = KSTypeFake(
            "declaration" to KSDeclarationFake(
                "packageName" to KSNameFake("com.example"),
                "simpleName" to KSNameFake("Media"),
                "parentDeclaration" to null,
            ),
            "arguments" to listOf(
                KSTypeArgumentFake(
                    "type" to KSTypeReferenceFake(
                        "resolve" to KSTypeFake(
                            "declaration" to KSDeclarationFake(
                                "packageName" to KSNameFake("com.example"),
                                "simpleName" to KSNameFake("Image"),
                                "parentDeclaration" to null,
                            ),
                            "arguments" to emptyList<KSTypeArgument>(),
                            "nullability" to Nullability.NOT_NULL,
                        ),
                    ),
                ),
            ),
            "nullability" to Nullability.NOT_NULL
        )

        // Act
        val poetTypeName = kspType.toTypeName()

        // Assert
        val poetClassName = (poetTypeName as ParameterizedTypeName).rawType
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
        assertFalse(poetClassName.isNullable)

        assertEquals(1, poetTypeName.typeArguments.size)
        val typeArgument = poetTypeName.typeArguments.first() as ClassName
        assertEquals("com.example", typeArgument.packageName)
        assertEquals("Image", typeArgument.simpleName)
        assertFalse(typeArgument.isNullable)
    }

    @Test
    fun `can convert a nullable KSP type to a Poet type name`() {
        // Arrange
        val kspType = KSTypeFake(
            "declaration" to KSDeclarationFake(
                "packageName" to KSNameFake("com.example"),
                "simpleName" to KSNameFake("Media"),
                "parentDeclaration" to null,
            ),
            "arguments" to emptyList<KSTypeArgument>(),
            "nullability" to Nullability.NULLABLE,
        )

        // Act
        val poetTypeName = kspType.toTypeName()

        // Assert
        val poetClassName = poetTypeName as ClassName
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
        assertTrue(poetClassName.isNullable)
    }

    @Test
    fun `doesn't convert a platform nullable KSP type to a nullable Poet type name`() {
        // Arrange
        val kspType = KSTypeFake(
            "declaration" to KSDeclarationFake(
                "packageName" to KSNameFake("com.example"),
                "simpleName" to KSNameFake("Media"),
                "parentDeclaration" to null,
            ),
            "arguments" to emptyList<KSTypeArgument>(),
            "nullability" to Nullability.PLATFORM,
        )

        // Act
        val poetTypeName = kspType.toTypeName()

        // Assert
        val poetClassName = poetTypeName as ClassName
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
        assertFalse(poetClassName.isNullable)
    }

    @Test
    fun `can convert a KSP type reference to a Poet type name`() {
        // Arrange
        val kspTypeReference = KSTypeReferenceFake(
            "resolve" to KSTypeFake(
                "declaration" to KSDeclarationFake(
                    "packageName" to KSNameFake("com.example"),
                    "simpleName" to KSNameFake("Media"),
                    "parentDeclaration" to null,
                ),
                "arguments" to emptyList<KSTypeArgument>(),
                "nullability" to Nullability.NOT_NULL,
            ),
        )

        // Act
        val poetTypeName = kspTypeReference.toTypeName()

        // Assert
        val poetClassName = poetTypeName as ClassName
        assertEquals("com.example", poetClassName.packageName)
        assertEquals("Media", poetClassName.simpleName)
        assertFalse(poetClassName.isNullable)
    }

    @Test
    fun `can convert a KSP property declaration to a Poet member name`() {
        // Arrange
        val kspPropertyDeclaration = KSPropertyDeclarationFake(
            "packageName" to KSNameFake("com.example"),
            "simpleName" to KSNameFake("name"),
            "parentDeclaration" to KSDeclarationFake(
                "packageName" to KSNameFake("com.example"),
                "simpleName" to KSNameFake("Media"),
                "parentDeclaration" to null,
            ),
        )

        // Act
        val poetMemberName = kspPropertyDeclaration.toMemberName()

        // Assert
        assertEquals("com.example", poetMemberName.packageName)
        assertEquals("name", poetMemberName.simpleName)
        assertEquals("com.example", poetMemberName.enclosingClassName!!.packageName)
        assertEquals("Media", poetMemberName.enclosingClassName!!.simpleName)
    }

    //region Fake implementations of the KSP API

    private class KSNodeFake(vararg values: Pair<String, Any?>) : KSNode {
        private val map = mapOf(*values)

        override val location: Location by map
        override val origin: Origin by map
        override val parent: KSNode? by map

        override fun <D, R> accept(visitor: KSVisitor<D, R>, data: D): R {
            TODO("Not yet implemented")
        }
    }

    private class KSAnnotatedFake(vararg values: Pair<String, Any?>) : KSAnnotated, KSNode by KSNodeFake(*values) {
        private val map = mapOf(*values)

        override val annotations: Sequence<KSAnnotation> by map
    }

    private class KSDeclarationFake(vararg values: Pair<String, Any?>) : KSDeclaration, KSAnnotated by KSAnnotatedFake(*values) {
        private val map = mapOf(*values)

        override val containingFile: KSFile? by map
        override val docString: String? by map
        override val isActual: Boolean by map
        override val isExpect: Boolean by map
        override val modifiers: Set<Modifier> by map
        override val packageName: KSName by map
        override val parentDeclaration: KSDeclaration? by map
        override val qualifiedName: KSName? by map
        override val simpleName: KSName by map
        override val typeParameters: List<KSTypeParameter> by map

        override fun findActuals(): Sequence<KSDeclaration> {
            TODO("Not yet implemented")
        }

        override fun findExpects(): Sequence<KSDeclaration> {
            TODO("Not yet implemented")
        }
    }

    @JvmInline
    private value class KSNameFake(val name: String) : KSName {
        override fun asString() = name
        override fun getQualifier(): String = TODO("Not yet implemented")
        override fun getShortName(): String = TODO("Not yet implemented")
    }

    private class KSTypeFake(vararg values: Pair<String, Any?>) : KSType {
        private val map = mapOf(*values)

        override val annotations: Sequence<KSAnnotation> by map
        override val arguments: List<KSTypeArgument> by map
        override val declaration: KSDeclaration by map
        override val isError: Boolean by map
        override val isFunctionType: Boolean by map
        override val isMarkedNullable: Boolean by map
        override val isSuspendFunctionType: Boolean by map
        override val nullability: Nullability by map

        override fun isAssignableFrom(that: KSType): Boolean = TODO("Not yet implemented")
        override fun isCovarianceFlexible(): Boolean = TODO("Not yet implemented")
        override fun isMutabilityFlexible(): Boolean = TODO("Not yet implemented")
        override fun makeNotNullable(): KSType = TODO("Not yet implemented")
        override fun makeNullable(): KSType = TODO("Not yet implemented")
        override fun replace(arguments: List<KSTypeArgument>): KSType = TODO("Not yet implemented")
        override fun starProjection(): KSType = TODO("Not yet implemented")
    }

    private class KSTypeArgumentFake(vararg values: Pair<String, Any?>) : KSTypeArgument, KSAnnotated by KSAnnotatedFake(*values) {
        private val map = mapOf(*values)

        override val type: KSTypeReference? by map
        override val variance: Variance by map
    }

    private class KSTypeReferenceFake(vararg values: Pair<String, Any?>) : KSTypeReference, KSAnnotated by KSAnnotatedFake(*values) {
        private val map = mapOf(*values)

        override val element: KSReferenceElement? by map
        override val modifiers: Set<Modifier> by map

        override fun resolve() = map["resolve"] as KSType
    }

    private class KSPropertyDeclarationFake(vararg values: Pair<String, Any?>) : KSPropertyDeclaration, KSDeclaration by KSDeclarationFake(*values) {
        private val map = mapOf(*values)

        override val extensionReceiver: KSTypeReference? by map
        override val getter: KSPropertyGetter? by map
        override val hasBackingField: Boolean by map
        override val isMutable: Boolean by map
        override val setter: KSPropertySetter? by map
        override val type: KSTypeReference by map

        override fun asMemberOf(containing: KSType): KSType = TODO("Not yet implemented")
        override fun findOverridee(): KSPropertyDeclaration? = TODO("Not yet implemented")
        override fun isDelegated(): Boolean = TODO("Not yet implemented")
    }

    //endregion
}
