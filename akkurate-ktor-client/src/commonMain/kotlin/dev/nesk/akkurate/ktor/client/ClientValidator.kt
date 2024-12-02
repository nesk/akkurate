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

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import kotlin.reflect.KClass

/**
 * A validator that should be registered with [AkkurateConfig.registerValidator].
 */
public interface ClientValidator {
    /**
     * Validates the [value].
     *
     * Throws a [ValidationResult.Exception] if the value matches the excepted type and failed the validation.
     */
    public suspend fun validate(value: Any?)

    /**
     * A validator for [Validator.Runner] instances.
     */
    public class Runner<ValueType>(
        private val valueType: KClass<*>,
        private val validator: Validator.Runner<ValueType>,
    ) : ClientValidator {
        override suspend fun validate(value: Any?) {
            if (!valueType.isInstance(value)) return

            @Suppress("UNCHECKED_CAST")
            validator(value as ValueType).orThrow()
        }

        /**
         * A validator for [Validator.Runner.WithContext] instances.
         */
        public class WithContext<ContextType, ValueType>(
            private val valueType: KClass<*>,
            private val validator: Validator.Runner.WithContext<ContextType, ValueType>,
            private val contextProvider: suspend () -> ContextType,
        ) : ClientValidator {
            override suspend fun validate(value: Any?) {
                if (!valueType.isInstance(value)) return

                @Suppress("UNCHECKED_CAST")
                validator(contextProvider(), value as ValueType).orThrow()
            }
        }
    }

    /**
     * A validator for [Validator.SuspendableRunner] instances.
     */
    public class SuspendableRunner<ValueType>(
        private val valueType: KClass<*>,
        private val validator: Validator.SuspendableRunner<ValueType>,
    ) : ClientValidator {
        override suspend fun validate(value: Any?) {
            if (!valueType.isInstance(value)) return

            @Suppress("UNCHECKED_CAST")
            validator(value as ValueType).orThrow()
        }

        /**
         * A validator for [Validator.SuspendableRunner.WithContext] instances.
         */
        public class WithContext<ContextType, ValueType>(
            private val valueType: KClass<*>,
            private val validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
            private val contextProvider: suspend () -> ContextType,
        ) : ClientValidator {
            override suspend fun validate(value: Any?) {
                if (!valueType.isInstance(value)) return

                @Suppress("UNCHECKED_CAST")
                validator(contextProvider(), value as ValueType).orThrow()
            }
        }
    }
}
