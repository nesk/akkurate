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

package dev.nesk.akkurate.ktor.client

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.builders.isTrue
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertIs

class AkkurateTest {
    @Test
    fun does_not_throw_when_the_payload_is_valid() = testApplication {
        respondBoolean(true)
        akkurateClient.get("/").body<Boolean>()
    }

    @Test
    fun throws_when_the_payload_is_invalid() = testApplication {
        respondBoolean(false)
        val exception = assertFails { akkurateClient.get("/").body<Boolean>() }
        assertIs<ValidationResult.Exception>(exception)
    }

    private companion object {
        private val validateTruthiness = Validator<Boolean> { isTrue() }

        val ApplicationTestBuilder.akkurateClient
            get() = createClient {
                install(Akkurate) {
                    registerValidator(validateTruthiness)
                }
                install(ContentNegotiation) {
                    json()
                }
            }
    }

    private fun ApplicationTestBuilder.respondBoolean(value: Boolean) {
        routing {
            get("/") {
                call.response.header(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                call.respondText(value.toString())
            }
        }
    }
}
