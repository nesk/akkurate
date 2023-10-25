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
    fun `the default configuration is viable`() {
        Configuration().let {
            assertTrue(it.defaultViolationMessage.isNotBlank(), "The default message is not blank")
            assertTrue(it.rootPath.isEmpty(), "The default root path is empty")
        }
    }

    @Test
    fun `all configuration options are customizable`() {
        val config = Configuration {
            defaultViolationMessage = "foo"
            rootPath = listOf("bar", "baz")
        }

        assertEquals("foo", config.defaultViolationMessage, "defaultViolationMessage is customizable")
        assertEquals(listOf("bar", "baz"), config.rootPath, "rootPath is customizable")
    }

    @Test
    fun `a new configuration can be generated based from a previous one`() {
        // Arrange
        val sourceConfig = Configuration {
            defaultViolationMessage = "foo"
            rootPath = listOf("bar", "baz")
        }

        // Act
        val alteredConfig = Configuration(sourceConfig) {
            defaultViolationMessage += "_"
            rootPath = rootPath.map { it + "_" }
        }

        // Assert
        assertEquals("foo_", alteredConfig.defaultViolationMessage, "defaultViolationMessage is altered")
        assertEquals(listOf("bar_", "baz_"), alteredConfig.rootPath, "rootPath is altered")
    }

    @Test
    fun `source configurations aren't mutated`() {
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
    fun `generated configurations cannot be mutated by keeping a reference to the builder`() {
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
    fun `'rootPath' function defines the property of the same name`() {
        val config = Configuration { rootPath("foo", "bar") }
        assertEquals(listOf("foo", "bar"), config.rootPath)
    }

    @Test
    fun `'equals' returns true when all the values are the same`() {
        assertTrue(Configuration().equals(Configuration()))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'defaultViolationMessage')`() {
        val original = Configuration()
        val other = Configuration { defaultViolationMessage = "foo" }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'equals' returns false when at least one of the values differ (variant 'rootPath')`() {
        val original = Configuration()
        val other = Configuration { rootPath("foo") }
        assertFalse(original.equals(other))
    }

    @Test
    fun `'hashCode' returns the same hash when all the values are the same`() {
        assertEquals(Configuration().hashCode(), Configuration().hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'defaultViolationMessage')`() {
        val original = Configuration()
        val other = Configuration { defaultViolationMessage = "foo" }
        assertNotEquals(original.hashCode(), other.hashCode())
    }

    @Test
    fun `'hashCode' returns different hashes when at least one of the values differ (variant 'rootPath')`() {
        val original = Configuration()
        val other = Configuration { rootPath("foo") }
        assertNotEquals(original.hashCode(), other.hashCode())
    }
}
