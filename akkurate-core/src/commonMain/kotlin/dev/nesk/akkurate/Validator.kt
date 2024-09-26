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

package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.*
import dev.nesk.akkurate.validatables.Validatable

public sealed interface Validator {
    public companion object {
        public operator fun <ContextType, ValueType> invoke(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.(context: ContextType) -> Unit,
        ): Runner.WithContext<ContextType, ValueType> = ValidatorRunner(configuration, block)

        public operator fun <ValueType> invoke(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.() -> Unit,
        ): Runner<ValueType> = ValidatorRunner.WithoutContext(configuration, block)

        public fun <ContextType, ValueType> suspendable(
            configuration: Configuration = Configuration(),
            block: suspend Validatable<ValueType>.(context: ContextType) -> Unit,
        ): SuspendableRunner.WithContext<ContextType, ValueType> = SuspendableValidatorRunner(configuration, block)

        public fun <ValueType> suspendable(
            configuration: Configuration = Configuration(),
            block: suspend Validatable<ValueType>.() -> Unit,
        ): SuspendableRunner<ValueType> = SuspendableValidatorRunner.WithoutContext(configuration, block)
    }

    public sealed interface Runner<ValueType> {
        public operator fun invoke(value: ValueType): ValidationResult<ValueType>

        public sealed interface WithContext<ContextType, ValueType> {
            public operator fun invoke(context: ContextType): Runner<ValueType>
            public operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType>
        }
    }

    public sealed interface SuspendableRunner<ValueType> {
        public suspend operator fun invoke(value: ValueType): ValidationResult<ValueType>

        public sealed interface WithContext<ContextType, ValueType> {
            public operator fun invoke(context: ContextType): SuspendableRunner<ValueType>
            public suspend operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType>
        }
    }
}

//region validateWith implementations

public fun <ContextType, ValueType> Validatable<ValueType>.validateWith(
    validator: Validator.Runner.WithContext<ContextType, ValueType>,
    context: ContextType,
) {
    validateWith(validator(context))
}

public fun <ValueType> Validatable<ValueType>.validateWith(validator: Validator.Runner<ValueType>) {
    val result = validator(unwrap())
    if (result is ValidationResult.Failure) {
        result.violations
            .map { it.copy(path = this@validateWith.path() + it.path) }
            .forEach(this::registerConstraint)
    }
}

public suspend fun <ContextType, ValueType> Validatable<ValueType>.validateWith(
    validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType>,
    context: ContextType,
) {
    validateWith(validator(context))
}

public suspend fun <ValueType> Validatable<ValueType>.validateWith(validator: Validator.SuspendableRunner<ValueType>) {
    val result = validator(unwrap())
    if (result is ValidationResult.Failure) {
        result.violations
            .map { it.copy(path = this@validateWith.path() + it.path) }
            .forEach(this::registerConstraint)
    }
}

//endregion

//region Private API

private class ValidatorRunner<ContextType, ValueType>(
    private val configuration: Configuration,
    private val block: Validatable<ValueType>.(context: ContextType) -> Unit,
) : Validator.Runner.WithContext<ContextType, ValueType> {

    override operator fun invoke(context: ContextType) = Contextualized(configuration, context, block)

    override operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType> = invoke(context)(value)

    class Contextualized<ContextType, ValueType>(
        private val configuration: Configuration,
        private val context: ContextType,
        private val block: Validatable<ValueType>.(context: ContextType) -> Unit,
    ) : Validator.Runner<ValueType> {
        override operator fun invoke(value: ValueType): ValidationResult<ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                Validatable(value, registry).run { block(context) }
            }
    }

    class WithoutContext<ValueType>(
        private val configuration: Configuration,
        private val block: Validatable<ValueType>.() -> Unit,
    ) : Validator.Runner<ValueType> {
        override operator fun invoke(value: ValueType): ValidationResult<ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                Validatable(value, registry).run { block() }
            }
    }

}

private class SuspendableValidatorRunner<ContextType, ValueType>(
    private val configuration: Configuration,
    private val block: suspend Validatable<ValueType>.(context: ContextType) -> Unit,
) : Validator.SuspendableRunner.WithContext<ContextType, ValueType> {

    override operator fun invoke(context: ContextType) = Contextualized(configuration, context, block)

    override suspend operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType> = invoke(context)(value)

    class Contextualized<ContextType, ValueType>(
        private val configuration: Configuration,
        private val context: ContextType,
        private val block: suspend Validatable<ValueType>.(context: ContextType) -> Unit,
    ) : Validator.SuspendableRunner<ValueType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                Validatable(value, registry).run { block(context) }
            }
    }

    class WithoutContext<ValueType>(
        private val configuration: Configuration,
        private val block: suspend Validatable<ValueType>.() -> Unit,
    ) : Validator.SuspendableRunner<ValueType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                Validatable(value, registry).run { block() }
            }
    }

}

//endregion
