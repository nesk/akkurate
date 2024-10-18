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
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.response.*

private val validateString = Validator<String> {}
private val validateContextualString = Validator<Unit, String> {}
private val suspendValidateString = Validator.suspendable<String> {}
private val suspendValidateContextualString = Validator.suspendable<Unit, String> {}

private fun Application.module() {
    install(RequestValidation) {
        registerValidator(validateString)
        registerValidator(validateContextualString) { }
        registerValidator(suspendValidateString)
        registerValidator(suspendValidateContextualString) { }
    }

    install(AkkurateValidation) {
        status = HttpStatusCode.BadRequest
        contentType = ContentType.Application.ProblemJson

        buildMessage { call, violations ->
            call.respond(ProblemDetailsMessage(violations))
        }
    }
}


