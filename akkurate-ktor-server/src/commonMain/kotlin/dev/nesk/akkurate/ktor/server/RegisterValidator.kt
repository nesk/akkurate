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
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*

/**
 * Registers a new [validator], which will be executed for each [received][receive] request body.
 * The [contextProvider] is called on each execution, then its result is feed to the validator.
 */
public inline fun <ContextType, reified ValueType : Any> RequestValidationConfig.registerValidator(
    validator: Validator.Runner.WithContext<ContextType, ValueType>,
    noinline contextProvider: suspend () -> ContextType,
) {
    validate<ValueType> {
        validator(contextProvider(), it).orThrow()
        ValidationResult.Valid
    }
}

/**
 * Registers a new [validator], which will be executed for each [received][receive] request body.
 */
public inline fun <reified ValueType : Any> RequestValidationConfig.registerValidator(validator: Validator.Runner<ValueType>) {
    validate<ValueType> {
        validator(it).orThrow()
        ValidationResult.Valid
    }
}

/**
 * Registers a new [validator], which will be executed for each [received][receive] request body.
 * The [contextProvider] is called on each execution, then its result is feed to the validator.
 */
public inline fun <ContextType, reified ValueType : Any> RequestValidationConfig.registerValidator(
    validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
    noinline contextProvider: suspend () -> ContextType,
) {
    validate<ValueType> {
        validator(contextProvider(), it).orThrow()
        ValidationResult.Valid
    }
}

/**
 * Registers a new [validator], which will be executed for each [received][receive] request body.
 */
public inline fun <reified ValueType : Any> RequestValidationConfig.registerValidator(validator: Validator.SuspendableRunner<ValueType>) {
    validate<ValueType> {
        validator(it).orThrow()
        ValidationResult.Valid
    }
}
