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

package dev.nesk.akkurate.validatables

import dev.nesk.akkurate.Path
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.*
import dev.nesk.akkurate.constraints.ConstraintRegistry
import dev.nesk.akkurate.validateWith
import kotlin.jvm.JvmName
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

@DslMarker
private annotation class ValidatableDslMarker

public typealias DefaultMetadataType = Map<String, String>
public typealias Validatable<T> = GenericValidatable<T, DefaultMetadataType>

@ValidatableDslMarker
public class GenericValidatable<out T, MetadataType> private constructor(
    private val wrappedValue: T,
    pathSegment: String?,
    private val constraintRegistry: GenericConstraintRegistry<MetadataType>,
    internal val parent: GenericValidatable<*, MetadataType>?,
    private val path: Path = buildPathFromParentAndSegment(parent, pathSegment),
    public val defaultMetadata: MetadataType
) {
    internal companion object {
        fun <MetadataType> buildPathFromParentAndSegment(parent: GenericValidatable<*, MetadataType>?, segment: String?) = buildList {
            addAll(parent?.path ?: emptyList())
            if (!segment.isNullOrEmpty()) {
                add(segment)
            }
        }

        operator fun <T> invoke(wrappedValue: T, pathSegment: String?, constraintRegistry: GenericConstraintRegistry<DefaultMetadataType>, parent: Validatable<*>?, path: Path): Validatable<T> =
            GenericValidatable(wrappedValue, pathSegment, constraintRegistry, parent, path, emptyMap())

        operator fun <T> invoke(wrappedValue: T, constraintRegistry: GenericConstraintRegistry<DefaultMetadataType>): Validatable<T> =
            GenericValidatable(wrappedValue, constraintRegistry, emptyMap())

        operator fun <T, MetadataType> invoke(wrappedValue: T, pathSegment: String?, constraintRegistry: GenericConstraintRegistry<MetadataType>, parent: GenericValidatable<*, MetadataType>?, path: Path, defaultMeta: MetadataType): GenericValidatable<T, MetadataType> =
            GenericValidatable(wrappedValue, pathSegment, constraintRegistry, parent, path, defaultMeta)
    }

    /**
     * Instantiates a root [GenericValidatable] with its [value][wrappedValue] and a [GenericConstraintRegistry].
     *
     * Used by [Validator] instances to start the validation process.
     */
    internal constructor(wrappedValue: T, constraintRegistry: GenericConstraintRegistry<MetadataType>, defaultMetadata: MetadataType) :
            this(wrappedValue, pathSegment = null, constraintRegistry, parent = null, defaultMetadata = defaultMetadata)

    /**
     * Instantiates a child [GenericValidatable] with [its relative path][pathSegment] and its [GenericValidatable parent][parent].
     *
     * Used by [validatableOf] functions to validate nested properties.
     */
    internal constructor(wrappedValue: T, pathSegment: String, parent: GenericValidatable<*, MetadataType>) :
            this(wrappedValue, pathSegment, parent.constraintRegistry, parent, defaultMetadata = parent.defaultMetadata)

    public fun path(): Path = path

    public fun unwrap(): T = wrappedValue

    public operator fun component1(): T = wrappedValue

    /**
     * Runs all the necessary checks before registering a constraint.
     *
     * This method shouldn't be called in user code, [constrain] should be used instead.
     */
    public fun runChecksBeforeConstraintRegistration(): Unit = constraintRegistry.checkFirstViolationConfiguration()

    /**
     * Registers the provided constraint if it's unsatisfied.
     *
     * This method shouldn't be called in user code, [constrain] should be used instead.
     */
    public fun registerConstraint(constraint: GenericConstraint<MetadataType>): Unit = constraintRegistry.register(constraint)

    /**
     * Registers the provided constraint violation.
     *
     * This method shouldn't be called in user code, [constrain] or [validateWith] should be used instead.
     */
    public fun registerConstraint(constraint: GenericConstraintViolation<MetadataType>): Unit = constraintRegistry.register(constraint)

    /**
     * Duplicates the [GenericValidatable] with the new provided [value]. The path remains the same.
     */
    public fun <NEW_TYPE> withValue(value: NEW_TYPE): GenericValidatable<NEW_TYPE, MetadataType> =
        GenericValidatable(value, pathSegment = null, constraintRegistry, parent, path, defaultMetadata)

    // TODO: Convert to extension function (breaking change) once JetBrains fixes imports: https://youtrack.jetbrains.com/issue/KTIJ-22147
    public inline operator fun invoke(block: GenericValidatable<T, MetadataType>.() -> Unit): Unit = this.block()

    /**
     * Indicates whether some other object is "equal to" this validatable.
     *
     * Validatables are only compared against the value returned by [unwrap].
     * This allows easy comparisons between two validatables:
     *
     * ```
     * Validator<UserRegistration> {
     *     constrain { password == passwordConfirmation }
     * }
     * ```
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GenericValidatable<*, MetadataType>

        return wrappedValue == other.wrappedValue
    }

    /**
     * Returns a hash code value for the object.
     *
     * The hashcode is only produced from the value returned by [unwrap],
     * according to [equals] implementation.
     */
    override fun hashCode(): Int = wrappedValue?.hashCode() ?: 0

    override fun toString(): String = "Validatable(unwrap=$wrappedValue, path=$path)"
}

public fun <T : Any, V, MetadataType> GenericValidatable<T, MetadataType>.validatableOf(getter: KProperty1<T, V>): GenericValidatable<V, MetadataType> {
    return GenericValidatable(getter.get(unwrap()), getter.name, this)
}

@JvmName("nullableValidatableOfProperty")
public fun <T : Any?, V, MetadataType> GenericValidatable<T, MetadataType>.validatableOf(getter: KProperty1<T & Any, V>): GenericValidatable<V?, MetadataType> {
    return GenericValidatable(unwrap()?.let { getter.get(it) }, getter.name, this)
}

public fun <T : Any, V, MetadataType> GenericValidatable<T, MetadataType>.validatableOf(getter: KFunction1<T, V>): GenericValidatable<V, MetadataType> {
    return GenericValidatable(getter.invoke(unwrap()), getter.name, this)
}

@JvmName("nullableValidatableOfFunction")
public fun <T : Any?, V, MetadataType> GenericValidatable<T, MetadataType>.validatableOf(getter: KFunction1<T & Any, V>): GenericValidatable<V?, MetadataType> {
    return GenericValidatable(unwrap()?.let { getter.invoke(it) }, getter.name, this)
}

/**
 * Returns a [GenericValidatable] wrapping the result of the given [transform] function.
 * The latter is applied to the wrapped value of the [GenericValidatable] receiver.
 */
public inline fun <T, R, MetadataType> GenericValidatable<T, MetadataType>.map(transform: (T) -> R): GenericValidatable<R, MetadataType> =
    withValue(unwrap().let(transform))
