package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.ConstraintDescriptor
import dev.nesk.akkurate.constraints.ConstraintViolation
import dev.nesk.akkurate.constraints.ConstraintViolationSet
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
    if (result is ValidationResult.Failure<ValueType>) {
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
    if (result is ValidationResult.Failure<ValueType>) {
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
        override operator fun invoke(value: ValueType): ValidationResult<ValueType> {
            val block = this.block
            return Validatable(value).apply { block(context) }.toResult(configuration)
        }
    }

    class WithoutContext<ValueType>(
        private val configuration: Configuration,
        private val block: Validatable<ValueType>.() -> Unit,
    ) : Validator.Runner<ValueType> {
        override operator fun invoke(value: ValueType): ValidationResult<ValueType> {
            val block = this.block
            return Validatable(value).apply(block).toResult(configuration)
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
        override suspend operator fun invoke(value: ValueType): ValidationResult<ValueType> {
            val block = this.block
            return Validatable(value).apply { block(context) }.toResult(configuration)
        }
    }

    class WithoutContext<ValueType>(
        private val configuration: Configuration,
        private val block: suspend Validatable<ValueType>.() -> Unit,
    ) : Validator.SuspendableRunner<ValueType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<ValueType> {
            val block = this.block
            return Validatable(value).apply { block() }.toResult(configuration)
        }
    }

}

private fun <T> Validatable<T>.toResult(configuration: Configuration): ValidationResult<T> {
    return if (constraints.isEmpty()) {
        ValidationResult.Success
    } else {
        ValidationResult.Failure(constraints.toViolationSet(configuration), unwrap())
    }
}

private fun Iterable<ConstraintDescriptor>.toViolationSet(configuration: Configuration): ConstraintViolationSet = map {
    // TODO: Benchmark `is` usage in all runtimes, compared to the visitor pattern.
    when (it) {
        is Constraint -> it.toConstraintViolation(configuration.defaultViolationMessage, configuration.rootPath)
        is ConstraintViolation -> it
    }
}.toSet().let(::ConstraintViolationSet)

//endregion
