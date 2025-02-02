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
import dev.nesk.akkurate.validatables.DefaultMetadataType
import dev.nesk.akkurate.validatables.GenericValidatable
import dev.nesk.akkurate.validatables.Validatable

public sealed interface Validator {
    public companion object {
        public operator fun <ContextType, ValueType> invoke(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.(context: ContextType) -> Unit,
        ): Runner.WithContext<ContextType, ValueType, DefaultMetadataType> = invoke(emptyMap(), configuration, block)

        public operator fun <ContextType, ValueType, MetadataType> invoke(
            defaultMetadata: MetadataType,
            configuration: Configuration = Configuration(),
            block: GenericValidatable<ValueType, MetadataType>.(context: ContextType) -> Unit,
        ): Runner.WithContext<ContextType, ValueType, MetadataType> = ValidatorRunner(defaultMetadata, configuration, block)

        public operator fun <ValueType> invoke(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.() -> Unit,
        ): Runner<ValueType, DefaultMetadataType> = invoke(emptyMap(), configuration, block)

        public operator fun <ValueType, MetadataType> invoke(
            defaultMetadata: MetadataType,
            configuration: Configuration = Configuration(),
            block: GenericValidatable<ValueType, MetadataType>.() -> Unit,
        ): Runner<ValueType, MetadataType> = ValidatorRunner.WithoutContext(defaultMetadata, configuration, block)

        public fun <ContextType, ValueType> suspendable(
            configuration: Configuration = Configuration(),
            block: suspend Validatable<ValueType>.(context: ContextType) -> Unit,
        ): SuspendableRunner.WithContext<ContextType, ValueType, DefaultMetadataType> = suspendable(emptyMap(), configuration, block)

        public fun <ContextType, ValueType, MetadataType> suspendable(
            defaultMetadata: MetadataType,
            configuration: Configuration = Configuration(),
            block: suspend GenericValidatable<ValueType, MetadataType>.(context: ContextType) -> Unit,
        ): SuspendableRunner.WithContext<ContextType, ValueType, MetadataType> = SuspendableValidatorRunner(defaultMetadata, configuration, block)

        public fun <ValueType> suspendable(
            configuration: Configuration = Configuration(),
            block: suspend Validatable<ValueType>.() -> Unit,
        ): SuspendableRunner<ValueType, DefaultMetadataType> = suspendable(emptyMap(), configuration, block)

        public fun <ValueType, MetadataType> suspendable(
            defaultMetadata: MetadataType,
            configuration: Configuration = Configuration(),
            block: suspend GenericValidatable<ValueType, MetadataType>.() -> Unit,
        ): SuspendableRunner<ValueType, MetadataType> = SuspendableValidatorRunner.WithoutContext(defaultMetadata, configuration, block)
    }

    public sealed interface Runner<ValueType, MetadataType> {
        public operator fun invoke(value: ValueType): ValidationResult<MetadataType, ValueType>

        public sealed interface WithContext<ContextType, ValueType, MetadataType> {
            public operator fun invoke(context: ContextType): Runner<ValueType, MetadataType>
            public operator fun invoke(context: ContextType, value: ValueType): ValidationResult<MetadataType, ValueType>
        }
    }

    public sealed interface SuspendableRunner<ValueType, MetadataType> {
        public suspend operator fun invoke(value: ValueType): ValidationResult<MetadataType, ValueType>

        public sealed interface WithContext<ContextType, ValueType, MetadataType> {
            public operator fun invoke(context: ContextType): SuspendableRunner<ValueType, MetadataType>
            public suspend operator fun invoke(context: ContextType, value: ValueType): ValidationResult<MetadataType, ValueType>
        }
    }
}

//region validateWith implementations

public fun <ContextType, ValueType, MetadataType> GenericValidatable<ValueType, MetadataType>.validateWith(
    validator: Validator.Runner.WithContext<ContextType, ValueType, MetadataType>,
    context: ContextType,
) {
    validateWith(validator(context))
}

public fun <ValueType, MetadataType> GenericValidatable<ValueType, MetadataType>.validateWith(validator: Validator.Runner<ValueType, MetadataType>) {
    val result = validator(unwrap())
    if (result is ValidationResult.Failure) {
        result.violations
            .map { it.copy(path = this@validateWith.path() + it.path) }
            .forEach(this::registerConstraint)
    }
}

public suspend fun <ContextType, ValueType, MetadataType> GenericValidatable<ValueType, MetadataType>.validateWith(
    validator: Validator.SuspendableRunner.WithContext<ContextType, ValueType, MetadataType>,
    context: ContextType,
) {
    validateWith(validator(context))
}

public suspend fun <ValueType, MetadataType> GenericValidatable<ValueType, MetadataType>.validateWith(validator: Validator.SuspendableRunner<ValueType, MetadataType>) {
    val result = validator(unwrap())
    if (result is ValidationResult.Failure) {
        result.violations
            .map { it.copy(path = this@validateWith.path() + it.path) }
            .forEach(this::registerConstraint)
    }
}

//endregion

//region Private API

private class ValidatorRunner<ContextType, ValueType, MetadataType>(
    private val defaultMetadata: MetadataType,
    private val configuration: Configuration,
    private val block: GenericValidatable<ValueType, MetadataType>.(context: ContextType) -> Unit,
) : Validator.Runner.WithContext<ContextType, ValueType, MetadataType> {

    override operator fun invoke(context: ContextType) = Contextualized(defaultMetadata, configuration, context, block)

    override operator fun invoke(context: ContextType, value: ValueType): ValidationResult<MetadataType, ValueType> = invoke(context)(value)

    class Contextualized<ContextType, ValueType, MetadataType>(
        private val defaultMetadata: MetadataType,
        private val configuration: Configuration,
        private val context: ContextType,
        private val block: GenericValidatable<ValueType, MetadataType>.(context: ContextType) -> Unit,
    ) : Validator.Runner<ValueType, MetadataType> {
        override operator fun invoke(value: ValueType): ValidationResult<MetadataType, ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                GenericValidatable<ValueType, MetadataType>(value, registry, defaultMetadata).run { block(context) }
            }
    }

    class WithoutContext<ValueType, MetadataType>(
        private val defaultMetadata: MetadataType,
        private val configuration: Configuration,
        private val block: GenericValidatable<ValueType, MetadataType>.() -> Unit,
    ) : Validator.Runner<ValueType, MetadataType> {
        override operator fun invoke(value: ValueType): ValidationResult<MetadataType, ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                GenericValidatable<ValueType, MetadataType>(value, registry, defaultMetadata).run { block() }
            }
    }

}

private class SuspendableValidatorRunner<ContextType, ValueType, MetadataType>(
    private val defaultMetadata: MetadataType,
    private val configuration: Configuration,
    private val block: suspend GenericValidatable<ValueType, MetadataType>.(context: ContextType) -> Unit,
) : Validator.SuspendableRunner.WithContext<ContextType, ValueType, MetadataType> {

    override operator fun invoke(context: ContextType) = Contextualized(defaultMetadata, configuration, context, block)

    override suspend operator fun invoke(context: ContextType, value: ValueType): ValidationResult<MetadataType, ValueType> = invoke(context)(value)

    class Contextualized<ContextType, ValueType, MetadataType>(
        private val defaultMetadata: MetadataType,
        private val configuration: Configuration,
        private val context: ContextType,
        private val block: suspend GenericValidatable<ValueType, MetadataType>.(context: ContextType) -> Unit,
    ) : Validator.SuspendableRunner<ValueType, MetadataType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<MetadataType, ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                GenericValidatable(value, registry, defaultMetadata).run { block(context) }
            }
    }

    class WithoutContext<ValueType, MetadataType>(
        private val defaultMetadata: MetadataType,
        private val configuration: Configuration,
        private val block: suspend GenericValidatable<ValueType, MetadataType>.() -> Unit,
    ) : Validator.SuspendableRunner<ValueType, MetadataType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<MetadataType, ValueType> =
            runWithConstraintRegistry(value, configuration) { registry ->
                val block = this.block
                GenericValidatable(value, registry, defaultMetadata).run { block() }
            }
    }

}

//endregion
