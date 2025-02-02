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

package dev.nesk.akkurate.ktor.client

import dev.nesk.akkurate.Validator

/**
 * A config for [Akkurate] plugin.
 */
public class AkkurateConfig internal constructor() {
    internal val validators: MutableList<ClientValidator> = mutableListOf()

    /**
     * Registers a new [validator], which will be executed for each deserialized response body.
     */
    public fun registerValidator(validator: ClientValidator) {
        validators += validator
    }

    /**
     * Registers a new [validator], which will be executed for each deserialized response body.
     * The [contextProvider] is called on each execution, then its result is feed to the validator.
     */
    public inline fun <ContextType, reified ValueType, MetadataType> registerValidator(
        validator: Validator.Runner.WithContext<ContextType, ValueType, MetadataType>,
        noinline contextProvider: suspend () -> ContextType,
    ) {
        registerValidator(ClientValidator.Runner.WithContext(ValueType::class, validator, contextProvider))
    }

    /**
     * Registers a new [validator], which will be executed for each deserialized response body.
     */
    public inline fun <reified ValueType, MetadataType> registerValidator(validator: Validator.Runner<ValueType, MetadataType>) {
        registerValidator(ClientValidator.Runner(ValueType::class, validator))
    }

    /**
     * Registers a new [validator], which will be executed for each deserialized response body.
     * The [contextProvider] is called on each execution, then its result is feed to the validator.
     */
    public inline fun <ContextType, reified ValueType, MetadataType> registerValidator(
        validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType, MetadataType>,
        noinline contextProvider: suspend () -> ContextType,
    ) {
        registerValidator(ClientValidator.SuspendableRunner.WithContext(ValueType::class, validator, contextProvider))
    }

    /**
     * Registers a new [validator], which will be executed for each deserialized response body.
     */
    public inline fun <reified ValueType, MetadataType> registerValidator(validator: Validator.SuspendableRunner<ValueType, MetadataType>) {
        registerValidator(ClientValidator.SuspendableRunner(ValueType::class, validator))
    }
}
