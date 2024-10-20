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
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.withPath
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class AkkurateValidationTest {
    @Test
    fun toto() = testApplication {
        install(AkkurateValidation)
        install(ContentNegotiation) { json() }

        routing {
            get("/") {
                val validate = Validator<Boolean> { constrain { it } withPath { absolute("a", "b") } }
                validate(false).orThrow()
            }
        }

        val response = client.get("/")
        println(response.bodyAsText())
        assertEquals(HttpStatusCode.UnprocessableEntity, response.status)
    }
}
