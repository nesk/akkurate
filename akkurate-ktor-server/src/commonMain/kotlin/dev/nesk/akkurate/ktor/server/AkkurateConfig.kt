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
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * A config for [Akkurate] plugin.
 */
public class AkkurateConfig internal constructor() {
    /**
     * The status code of the response, in case of validation failure.
     *
     * Applying the status code is the responsibility of the response builder,
     * be aware of this behavior when you define your own via [buildResponse].
     */
    public var status: HttpStatusCode = HttpStatusCode.UnprocessableEntity

    /**
     * The content type of the response, in case of validation failure.
     *
     * Applying the content type is the responsibility of the response builder,
     * be aware of this behavior when you define your own via [buildResponse].
     */
    public var contentType: ContentType = ContentType.Application.ProblemJson

    internal var responseBuilder: suspend (call: ApplicationCall, violations: ConstraintViolationSet) -> Unit = { call, violations ->
        call.response.status(status)
        call.response.header(HttpHeaders.ContentType, contentType.toString())
        call.respond(ProblemDetailsMessage(status.value, violations))
    }

    /**
     * Defines your own builder to generate a custom response on validation failure.
     *
     * The [status] and [contentType][AkkurateConfig.contentType] properties are applied by the
     * default response builder but, once you define your own, it becomes your responsibility.
     */
    public fun buildResponse(block: suspend (call: ApplicationCall, violations: ConstraintViolationSet) -> Unit) {
        responseBuilder = block
    }
}
