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

import dev.nesk.akkurate.constraints.ConstraintViolationSet
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertIs

class AkkurateConfigTestJvm {
    @Test
    fun the_default_message_builder_returns_a__ProblemDetailsMessage__(): Unit = runBlocking {
        // Arrange
        val messageSlot = slot<Any>()
        val call = mockk<ApplicationCall>(relaxed = true)
        coEvery { call.respond(capture(messageSlot), any(TypeInfo::class)) } returns Unit

        // Act
        val config = AkkurateConfig()
        config.responseBuilder(call, ConstraintViolationSet(emptySet()))

        // Assert
        assertIs<ProblemDetailsMessage>(messageSlot.captured)
    }
}
