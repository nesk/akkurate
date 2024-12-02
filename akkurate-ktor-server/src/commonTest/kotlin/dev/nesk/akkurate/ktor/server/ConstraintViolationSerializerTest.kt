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

package dev.nesk.akkurate.ktor.server

import dev.nesk.akkurate.constraints.ConstraintViolation
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ConstraintViolationSerializerTest {
    @Test
    fun a_serialized_and_deserialized__ConstraintViolation__is_equal_to_the_original() {
        val expectedViolation = ConstraintViolation("foo", listOf("bar", "baz"))
        val jsonString = Json.encodeToString(ConstraintViolationSerializer, expectedViolation)
        val actualViolation = Json.decodeFromString(ConstraintViolationSerializer, jsonString)
        assertEquals(expectedViolation, actualViolation)
    }

    @Test
    fun the_path_is_serialized_to_a_dot_separated_string() {
        val violation = ConstraintViolation("", listOf("foo", "bar", "baz"))
        val jsonString = Json.encodeToString(ConstraintViolationSerializer, violation)
        val fields: Map<String, String> = Json.decodeFromString(jsonString)
        assertEquals("foo.bar.baz", fields["path"])
    }

    @Test
    fun the_path_cannot_contain_consecutive_dots_or_start_or_end_with_a_dot() {
        val violation = ConstraintViolation("", listOf(".foo.", ".bar.", ".baz."))
        val jsonString = Json.encodeToString(ConstraintViolationSerializer, violation)
        val fields: Map<String, String> = Json.decodeFromString(jsonString)
        assertEquals("foo.bar.baz", fields["path"])
    }
}
