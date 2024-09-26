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

import kotlin.test.*

class ConfigurationTest {
    @Test
    fun the_default_configuration_is_viable() {
        Configuration().let {
            assertTrue(it.defaultViolationMessage.isNotBlank(), "The default message is not blank")
            assertTrue(it.rootPath.isEmpty(), "The default root path is empty")
            assertFalse(it.failOnFirstViolation, "By default, it doesn't fail on the first constraint")
        }
    }

    @Test
    fun all_configuration_options_are_customizable() {
        val config = Configuration {
            defaultViolationMessage = "foo"
            rootPath = listOf("bar", "baz")
            failOnFirstViolation = true
        }

        assertEquals("foo", config.defaultViolationMessage, "defaultViolationMessage is customizable")
        assertEquals(listOf("bar", "baz"), config.rootPath, "rootPath is customizable")
        assertTrue(config.failOnFirstViolation, "failOnFirstViolation is customizable")
    }

    @Test
    fun a_new_configuration_can_be_generated_based_from_a_previous_one() {
        // Arrange
        val sourceConfig = Configuration {
            defaultViolationMessage = "foo"
            rootPath = listOf("bar", "baz")
            failOnFirstViolation = true
        }

        // Act
        val alteredConfig = Configuration(sourceConfig) {
            defaultViolationMessage += "_"
            rootPath = rootPath.map { it + "_" }
            failOnFirstViolation = failOnFirstViolation
        }

        // Assert
        assertEquals("foo_", alteredConfig.defaultViolationMessage, "defaultViolationMessage is altered")
        assertEquals(listOf("bar_", "baz_"), alteredConfig.rootPath, "rootPath is altered")
        assertTrue(alteredConfig.failOnFirstViolation, "failOnFirstViolation is altered")
    }

    @Test
    fun source_configurations_are_not_mutated() {
        // Arrange
        val sourceConfig = Configuration {
            defaultViolationMessage = "foo"
        }

        // Act
        Configuration(sourceConfig) {
            defaultViolationMessage += "_"
        }

        // Assert
        assertEquals("foo", sourceConfig.defaultViolationMessage)
    }

    @Test
    fun generated_configurations_cannot_be_mutated_by_keeping_a_reference_to_the_builder() {
        // Arrange
        lateinit var builder: Configuration.Builder
        val config = Configuration {
            builder = this
            defaultViolationMessage = "foo"
        }

        // Act
        builder.defaultViolationMessage = "bar"

        // Assert
        assertEquals("foo", config.defaultViolationMessage)
    }

    @Test
    fun __rootPath__function_defines_the_property_of_the_same_name() {
        val config = Configuration { rootPath("foo", "bar") }
        assertEquals(listOf("foo", "bar"), config.rootPath)
    }

    //region equals/hashCode/toString
    @Test
    fun __equals__returns_true_when_all_the_values_are_the_same() {
        assertTrue(Configuration().equals(Configuration()))
    }

    @Test
    fun __equals__returns_false_when_at_least_one_of_the_values_differ____variant_defaultViolationMessage() {
        val original = Configuration()
        val other = Configuration { defaultViolationMessage = "foo" }
        assertFalse(original.equals(other))
    }

    @Test
    fun __equals__returns_false_when_at_least_one_of_the_values_differ____variant_rootPath() {
        val original = Configuration()
        val other = Configuration { rootPath("foo") }
        assertFalse(original.equals(other))
    }

    @Test
    fun __equals__returns_false_when_at_least_one_of_the_values_differ____variant_failOnFirstViolation() {
        val original = Configuration()
        val other = Configuration { failOnFirstViolation = true }
        assertFalse(original.equals(other))
    }

    @Test
    fun __hashCode__returns_the_same_hash_when_all_the_values_are_the_same() {
        assertEquals(Configuration().hashCode(), Configuration().hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_defaultViolationMessage() {
        val original = Configuration()
        val other = Configuration { defaultViolationMessage = "foo" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_rootPath() {
        val original = Configuration()
        val other = Configuration { rootPath("foo") }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun __hashCode__returns_different_hashes_when_at_least_one_of_the_values_differ____variant_failOnFirstViolation() {
        val original = Configuration()
        val other = Configuration { failOnFirstViolation = true }
        assertNotEquals(original.hashCode(), other.hashCode())
    }
    //endregion
}
