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

public class AkkurateConfig internal constructor() {
    public var catchException: Boolean = true

    public var status: HttpStatusCode = HttpStatusCode.UnprocessableEntity

    public var contentType: ContentType = ContentType.Application.ProblemJson

    internal var responseBuilder: suspend (call: ApplicationCall, violations: ConstraintViolationSet) -> Unit = { call, violations ->
        call.response.status(status)
        call.response.header(HttpHeaders.ContentType, contentType.toString())
        call.respond(ProblemDetailsMessage(violations))
    }

    public fun buildResponse(block: suspend (call: ApplicationCall, violations: ConstraintViolationSet) -> Unit) {
        responseBuilder = block
    }
}
