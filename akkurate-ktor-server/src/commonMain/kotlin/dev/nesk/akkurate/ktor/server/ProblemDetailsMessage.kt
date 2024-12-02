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
import kotlinx.serialization.Serializable

/**
 *
 */
@Serializable
public class ProblemDetailsMessage(
    /**
     * A JSON number indicating [the HTTP status code](https://www.rfc-editor.org/rfc/rfc9110#section-15)
     * generated by the origin server for this occurrence of the problem.
     */
    public val status: Int,
    /**
     * Fields that failed validation, with their error message.
     */
    public val fields: Set<@Serializable(with = ConstraintViolationSerializer::class) ConstraintViolation>,
) {
    /**
     * A URI reference that identifies the problem type.
     */
    public val type: String = "https://akkurate.dev/validation-error"

    /**
     * A short, human-readable summary of the problem type.
     */
    public val title: String = "The payload is invalid"

    /**
     * A human-readable explanation specific to this occurrence of the problem.
     */
    public val detail: String = "The payload has been successfully parsed, but the server is unable to accept it due to validation errors."
}
