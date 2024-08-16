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

package dev.nesk.akkurate

import dev.nesk.akkurate._test.Validatable
import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertContentEquals

class PathTest {
    @Test
    fun `generating an absolute path doesn't reuse the validatable path`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = PathBuilder(validatable).absolute("bar", "baz")
        // Assert
        assertContentEquals(listOf("bar", "baz"), path)
    }

    @Test
    fun `generating a relative path appends the provided segments to the path of the parent validatable`() {
        // Arrange
        val parent = Validatable(null, "foo")
        val child = Validatable(null, "bar", parent)
        // Act
        val path = PathBuilder(child).relative("baz")
        // Assert
        assertContentEquals(listOf("foo", "baz"), path)
    }

    @Test
    fun `generating a relative path without a parent is the same as an absolute path`() {
        // Arrange
        val orphan = Validatable(null)
        // Act
        val path = PathBuilder(orphan).relative("bar")
        // Assert
        assertContentEquals(listOf("bar"), path)
    }

    @Test
    fun `generating an appended path returns the validatable path appended with the provided segments`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = PathBuilder(validatable).appended("bar")
        // Assert
        assertContentEquals(listOf("foo", "bar"), path)
    }

    @Test
    fun `calling 'path' on a validatable returns a builder associated to this validatable`() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = validatable.path { appended("bar") }
        // Assert
        assertContentEquals(listOf("foo", "bar"), path)
    }
}
