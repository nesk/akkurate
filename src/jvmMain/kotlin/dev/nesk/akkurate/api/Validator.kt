package dev.nesk.akkurate.api

interface Validator {
    companion object {
        operator fun <ContextType, ValueType> invoke(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.(context: ContextType) -> Unit
        ): Runner.WithContext<ContextType, ValueType> = ValidatorRunner(block)

        operator fun <ValueType> invoke(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.() -> Unit
        ): Runner<ValueType> = ValidatorRunner.WithoutContext(block)

        fun <ContextType, ValueType> suspendable(
            configuration: Configuration = Configuration(),
            block: suspend Validatable<ValueType>.(context: ContextType) -> Unit
        ): SuspendableRunner.WithContext<ContextType, ValueType> = SuspendableValidatorRunner(block)

        fun <ValueType> suspendable(
            configuration: Configuration = Configuration(),
            block: suspend Validatable<ValueType>.() -> Unit
        ): SuspendableRunner<ValueType> = SuspendableValidatorRunner.WithoutContext(block)
    }

    interface Configuration {
        companion object {
            operator fun invoke(block: Builder.() -> Unit = {}): Configuration = TODO()
        }

        val rootPath: List<String>

        interface Builder: Configuration {
            override var rootPath: MutableList<String>
        }
    }

    interface Runner<ValueType> {
        operator fun invoke(value: ValueType): ValidationResult<ValueType>

        interface WithContext<ContextType, ValueType>
        {
            operator fun invoke(context: ContextType): Runner<ValueType>
            operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType>
        }
    }

    interface SuspendableRunner<ValueType> {
        suspend operator fun invoke(value: ValueType): ValidationResult<ValueType>

        interface WithContext<ContextType, ValueType>
        {
            operator fun invoke(context: ContextType): SuspendableRunner<ValueType>
            suspend operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType>
        }
    }
}

private class ValidatorRunner<ContextType, ValueType>(private val block: Validatable<ValueType>.(context: ContextType) -> Unit)
    : Validator.Runner.WithContext<ContextType, ValueType> {
    override operator fun invoke(context: ContextType) = Contextualized(context, block)
    override operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType> = TODO()

    class Contextualized<ContextType, ValueType>(
        private val context: ContextType,
        private val block: Validatable<ValueType>.(context: ContextType) -> Unit
    ): Validator.Runner<ValueType> {
        override operator fun invoke(value: ValueType): ValidationResult<ValueType> = TODO()
    }

    class WithoutContext<ValueType>(private val block: Validatable<ValueType>.() -> Unit): Validator.Runner<ValueType> {
        override operator fun invoke(value: ValueType): ValidationResult<ValueType> = TODO()
    }
}

private class SuspendableValidatorRunner<ContextType, ValueType>(private val block: suspend Validatable<ValueType>.(context: ContextType) -> Unit)
    : Validator.SuspendableRunner.WithContext<ContextType, ValueType> {
    override operator fun invoke(context: ContextType) = Contextualized(context, block)
    override suspend operator fun invoke(context: ContextType, value: ValueType): ValidationResult<ValueType> = TODO()

    class Contextualized<ContextType, ValueType>(
        private val context: ContextType,
        private val block: suspend Validatable<ValueType>.(context: ContextType) -> Unit
    ): Validator.SuspendableRunner<ValueType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<ValueType> = TODO()
    }

    class WithoutContext<ValueType>(private val block: suspend Validatable<ValueType>.() -> Unit): Validator.SuspendableRunner<ValueType> {
        override suspend operator fun invoke(value: ValueType): ValidationResult<ValueType> = TODO()
    }
}
