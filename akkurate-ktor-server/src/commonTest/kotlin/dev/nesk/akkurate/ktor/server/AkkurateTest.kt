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

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.builders.isTrue
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

class AkkurateTest {
    @Test
    fun can_catch_validation_errors_with_default_configuration() = testApplication {
        install(Akkurate)
        installOtherPlugins()

        receiveBoolean()

        val response = client.sendBoolean(false)

        assertEquals(HttpStatusCode.UnprocessableEntity, response.status, "The status is 422")
        assertEquals(ContentType.Application.ProblemJson.toString(), response.headers[HttpHeaders.ContentType], "The content-type is application/problem+json")
        assertEquals(
            """{"status":422,"fields":[{"message":"Must be true","path":""}],"type":"https://akkurate.dev/validation-error","title":"The payload is invalid","detail":"The payload has been successfully parsed, but the server is unable to accept it due to validation errors."}""",
            response.bodyAsText(),
            "The JSON body contains the validation errors, a type, and a title."
        )
    }

    @Test
    fun can_change_the_status_and_content_type() = testApplication {
        install(Akkurate) {
            status = HttpStatusCode.BadRequest
            contentType = ContentType.Text.Plain
        }
        installOtherPlugins()

        receiveBoolean()

        val response = client.sendBoolean(false)

        assertEquals(HttpStatusCode.BadRequest, response.status, "The status is changed to 400")
        assertEquals(ContentType.Text.Plain.toString(), response.headers[HttpHeaders.ContentType], "The content-type is changed to text/plain")
    }

    @Test
    fun can_build_a_custom_response() = testApplication {
        install(Akkurate) {
            buildResponse { call, violations ->
                call.respond(violations.toString())
            }
        }
        installOtherPlugins()

        receiveBoolean()

        val response = client.sendBoolean(false)

        assertEquals(
            "ConstraintViolationSet(messages=[ConstraintViolation(message='Must be true', path=[], metadata={})])",
            response.bodyAsText(),
            "The body contains a string representation of ConstraintViolationSet."
        )
    }

    @Test
    fun does_not_catch_other_exceptions() = testApplication {
        install(Akkurate)
        installOtherPlugins()

        routing {
            post("/") { throw IllegalArgumentException() }
        }

        val exception = assertFails { client.post("/") }
        assertIs<IllegalArgumentException>(exception)
    }

    private companion object {
        private val validateTruthiness = Validator<Boolean> { isTrue() }
    }

    private fun ApplicationTestBuilder.installOtherPlugins() {
        install(RequestValidation) { registerValidator(validateTruthiness) }
        install(ContentNegotiation) { json() }
    }

    private fun ApplicationTestBuilder.receiveBoolean() {
        routing {
            post("/") {
                call.receive<Boolean>()
            }
        }
    }

    private suspend fun HttpClient.sendBoolean(value: Boolean) = post("/") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        setBody(value.toString())
    }

}
