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

import dev.nesk.akkurate.validatables.Validatable

/**
 * A drop-in internal class to automatically write validation results to STDOUT.
 *
 * Internal usage to easily document the constraint builders.
 *
 * Without:
 * ```
 * @Test
 * fun test() {
 *     val validate = Validator<Duration> { isNegative() }
 *     validate((-1).toDuration(DurationUnit.SECONDS))
 *     validate(1.toDuration(DurationUnit.SECONDS))
 * }
 *
 * // STDOUT:
 * // <nothing>
 * ```
 *
 * With:
 * ```
 * @Test
 * fun test() = AkkurateScratch {
 *     val validate = Validator<Duration> { isNegative() }
 *     validate((-1).toDuration(DurationUnit.SECONDS))
 *     validate(1.toDuration(DurationUnit.SECONDS))
 * }
 *
 * // STDOUT:
 * // Success
 * // Failure (message: Must be negative)
 * ```
 */
internal class AkkurateScratch {
    public companion object {
        public operator fun invoke(block: BlockContext.() -> Unit) {
            BlockContext.block()
        }
    }

    public object BlockContext {
        public fun <ValueType> Validator(
            configuration: Configuration = Configuration(),
            block: Validatable<ValueType>.() -> Unit,
        ): Validator.Runner<ValueType> = ValidatorWrapper(dev.nesk.akkurate.Validator(configuration, block))
    }

    public class ValidatorWrapper<ValueType>(
        private val delegate: Validator.Runner<ValueType>,
    ) : Validator.Runner<ValueType> by delegate {
        override fun invoke(value: ValueType): ValidationResult<ValueType> {
            return delegate(value).also { result ->
                when (result) {
                    is ValidationResult.Success -> println("Success")
                    is ValidationResult.Failure -> println("Failure (message: ${result.violations.single().message})")
                }
            }
        }
    }
}
