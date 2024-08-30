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

package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Configuration
import dev.nesk.akkurate.ValidationResult
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A storage for all the unsatisfied constraints encountered during a validation.
 */
internal class ConstraintRegistry(private val configuration: Configuration) {
    private val constraints = mutableSetOf<ConstraintDescriptor>()

    /**
     * Runs all the checks for [Configuration.failOnFirstViolation]. Should be called between each constraint registration.
     *
     * @throws FirstViolationException If an unsatisfied constraint is registered and [Configuration.failOnFirstViolation] is `true`.
     */
    fun checkFirstViolationConfiguration() {
        if (!configuration.failOnFirstViolation) return
        if (constraints.isEmpty()) return

        throw FirstViolationException(constraints.first().toConstraintViolation(configuration))
    }

    /**
     * Registers the provided constraint if it's unsatisfied.
     */
    fun register(constraint: Constraint) {
        if (!constraint.satisfied) constraints += constraint
    }

    /**
     * Registers the provided constraint violation.
     */
    fun register(constraint: ConstraintViolation) {
        constraints += constraint
    }

    fun toSet() = constraints.toSet()
}

/**
 * Runs the provided [block] with a new [ConstraintRegistry] provided as a parameter.
 *
 * @param value The value to validate, will be used if the validation is successful.
 * @return A [ValidationResult] based on the registered constraints in the [ConstraintRegistry].
 */
internal inline fun <T> runWithConstraintRegistry(
    value: T, configuration: Configuration, block: (ConstraintRegistry) -> Unit,
) = ConstraintRegistry(configuration)
    .alsoCatching<ConstraintRegistry, FirstViolationException>(block)
    .map { it.toSet() }
    .recover { setOf((it as FirstViolationException).violation) }
    .getOrThrow()
    .takeIf { it.isNotEmpty() }
    ?.let { ValidationResult.Failure(it.toViolationSet(configuration)) }
    ?: ValidationResult.Success(value)

private fun Iterable<ConstraintDescriptor>.toViolationSet(configuration: Configuration) =
    map { it.toConstraintViolation(configuration) }
        .toSet()
        .let(::ConstraintViolationSet)

/**
 * Calls the specified function [block] with `this` value as its argument and returns a result.
 *
 * @throws Throwable If an exception has been throw and doesn't match [E].
 * @return A [successful result][Result] if no exception happened, a [failed result][Result] if an exception matching [E] has been thrown.
 */
@OptIn(ExperimentalContracts::class)
private inline fun <T, reified E : Throwable> T.alsoCatching(block: (T) -> Unit): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return try {
        block(this)
        Result.success(this)
    } catch (e: Throwable) {
        if (e !is E) {
            throw e
        }
        Result.failure(e)
    }
}

// TODO: Benchmark `is` usage in all runtimes, compared to the visitor pattern.
private fun ConstraintDescriptor.toConstraintViolation(configuration: Configuration) = when (this) {
    is Constraint -> toConstraintViolation(configuration.defaultViolationMessage, configuration.rootPath)
    is ConstraintViolation -> this
}

/**
 * An internal exception used in conjunction with [Configuration.failOnFirstViolation].
 */
internal class FirstViolationException(val violation: ConstraintViolation) : RuntimeException()
