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

package dev.nesk.akkurate.ktor.server.client

import dev.nesk.akkurate.Validator
import io.ktor.client.*
import io.ktor.client.plugins.api.*

private val validateString = Validator<String> {}
private val validateContextualString = Validator<Unit, String> {}
private val suspendValidateString = Validator.suspendable<String> {}
private val suspendValidateContextualString = Validator.suspendable<Unit, String> {}

private fun HttpClientConfig<*>.derp() {
    install(AkkurateValidation) {
        registerValidator(validateString)
        registerValidator(Unit, validateContextualString)
        registerValidator(validateContextualString) { }
        registerValidator(suspendValidateString)
        registerValidator(Unit, suspendValidateContextualString)
        registerValidator(suspendValidateContextualString) { }
    }
}

// API MOCKS
// API MOCKS
// API MOCKS

public val AkkurateValidation: ClientPlugin<AkkurateValidationConfiguration> = createClientPlugin(
    name = "AkkurateValidation",
    createConfiguration = ::AkkurateValidationConfiguration
) {
    //
}

public class AkkurateValidationConfiguration internal constructor() {
    public inline fun <ContextType, reified ValueType : Any> registerValidator(
        context: ContextType,
        validator: Validator.Runner.WithContext<ContextType, ValueType>,
    ) {
        registerValidator(validator) { context }
    }

    public inline fun <ContextType, reified ValueType : Any> registerValidator(
        validator: Validator.Runner.WithContext<ContextType, ValueType>,
        contextProvider: () -> ContextType,
    ) {
        //
    }

    public inline fun <reified ValueType : Any> registerValidator(validator: Validator.Runner<ValueType>) {
        //
    }

    public inline fun <ContextType, reified ValueType : Any> registerValidator(
        context: ContextType,
        validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
    ) {
        registerValidator(validator) { context }
    }

    public inline fun <ContextType, reified ValueType : Any> registerValidator(
        validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
        contextProvider: () -> ContextType,
    ) {
        //
    }

    public inline fun <reified ValueType : Any> registerValidator(validator: Validator.SuspendableRunner<ValueType>) {
        //
    }
}
