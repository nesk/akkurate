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

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.ConstraintViolationSet
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.response.*

private val validateString = Validator<String> {}
private val validateContextualString = Validator<Unit, String> {}
private val suspendValidateString = Validator.suspendable<String> {}
private val suspendValidateContextualString = Validator.suspendable<Unit, String> {}

private fun Application.derp() {
    install(RequestValidation) {
        registerValidator(validateString)
        registerValidator(Unit, validateContextualString)
        registerValidator(validateContextualString) { }
        registerValidator(suspendValidateString)
        registerValidator(Unit, suspendValidateContextualString)
        registerValidator(suspendValidateContextualString) { }
    }

    install(AkkurateValidation) {
        status = HttpStatusCode.BadRequest
        contentType = ContentType.Application.ProblemJson

        buildResponse { call, violations ->
            call.respond(Rfc9457ResponsePayload(violations))
        }
    }
}

// API MOCKS
// API MOCKS
// API MOCKS

public val AkkurateValidation: ApplicationPlugin<AkkurateValidationConfiguration> = createApplicationPlugin(
    name = "AkkurateValidation",
    createConfiguration = ::AkkurateValidationConfiguration
) {
    on(CallFailed) { call, cause ->
        with(pluginConfig) {
            if (!catchValidationException) return@on

            if (cause !is ValidationResult.Exception) throw cause

            call.response.status(status)
            call.response.headers.append(HttpHeaders.ContentType, contentType.toString())
            call.respond(responseBuilder)
        }
    }
}

public class AkkurateValidationConfiguration internal constructor() {
    public var catchValidationException: Boolean = true

    public var status: HttpStatusCode = HttpStatusCode.BadRequest
    public var contentType: ContentType = ContentType.Application.ProblemJson

    internal var responseBuilder: suspend (call: ApplicationCall, violations: ConstraintViolationSet) -> Unit = { call, violations ->
        call.respond(Rfc9457ResponsePayload(violations))
    }

    public fun buildResponse(block: suspend (call: ApplicationCall, violations: ConstraintViolationSet) -> Unit) {
        responseBuilder = block
    }
}

public class Rfc9457ResponsePayload(violations: ConstraintViolationSet) {
    public val type: String = "https://akkurate.dev/validation-error"
    public val title: String = "The payload is invalid"
    public val fields: ConstraintViolationSet = violations
}

public inline fun <ContextType, reified ValueType : Any> RequestValidationConfig.registerValidator(
    context: ContextType,
    validator: Validator.Runner.WithContext<ContextType, ValueType>,
) {
    //
}

public inline fun <ContextType, reified ValueType : Any> RequestValidationConfig.registerValidator(
    validator: Validator.Runner.WithContext<ContextType, ValueType>,
    contextProvider: () -> ContextType,
) {
    //
}

public inline fun <reified ValueType : Any> RequestValidationConfig.registerValidator(validator: Validator.Runner<ValueType>) {
    //
}

public inline fun <ContextType, reified ValueType : Any> RequestValidationConfig.registerValidator(
    context: ContextType,
    validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
) {
    //
}

public inline fun <ContextType, reified ValueType : Any> RequestValidationConfig.registerValidator(
    validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
    contextProvider: () -> ContextType,
) {
    //
}

public inline fun <reified ValueType : Any> RequestValidationConfig.registerValidator(validator: Validator.SuspendableRunner<ValueType>) {
    //
}
