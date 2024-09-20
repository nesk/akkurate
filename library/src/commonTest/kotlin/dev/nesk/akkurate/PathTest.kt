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
import dev.nesk.akkurate.test.Validatable
import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertContentEquals

class PathTest {
    @Test
    fun generating_an_absolute_path_does_not_reuse_the_validatable_path() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = PathBuilder(validatable).absolute("bar", "baz")
        // Assert
        assertContentEquals(listOf("bar", "baz"), path)
    }

    @Test
    fun generating_a_relative_path_appends_the_provided_segments_to_the_path_of_the_parent_validatable() {
        // Arrange
        val parent = Validatable(null, "foo")
        val child = Validatable(null, "bar", parent)
        // Act
        val path = PathBuilder(child).relative("baz")
        // Assert
        assertContentEquals(listOf("foo", "baz"), path)
    }

    @Test
    fun generating_a_relative_path_without_a_parent_is_the_same_as_an_absolute_path() {
        // Arrange
        val orphan = Validatable(null)
        // Act
        val path = PathBuilder(orphan).relative("bar")
        // Assert
        assertContentEquals(listOf("bar"), path)
    }

    @Test
    fun generating_an_appended_path_returns_the_validatable_path_appended_with_the_provided_segments() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = PathBuilder(validatable).appended("bar")
        // Assert
        assertContentEquals(listOf("foo", "bar"), path)
    }

    @Test
    fun calling__path__on_a_validatable_returns_a_builder_associated_to_this_validatable() {
        // Arrange
        val validatable = Validatable(null, "foo")
        // Act
        val path = validatable.path { appended("bar") }
        // Assert
        assertContentEquals(listOf("foo", "bar"), path)
    }
}
