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
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*

/**
 * A plugin that validates received request bodies.
 *
 * ```
 * fun Application.configureSerialization() {
 *     install(Akkurate)
 *
 *     install(ContentNegotiation) {
 *         json()
 *     }
 *     install(RequestValidation) {
 *         registerValidator(validateBook)
 *     }
 * }
 * ```
 */
public val Akkurate: ApplicationPlugin<AkkurateConfig> = createApplicationPlugin(
    name = "Akkurate",
    createConfiguration = ::AkkurateConfig
) {
    on(CallFailed) { call, cause ->
        with(pluginConfig) {
            if (cause !is ValidationResult.Exception) throw cause
            responseBuilder(call, cause.violations)
        }
    }
}
