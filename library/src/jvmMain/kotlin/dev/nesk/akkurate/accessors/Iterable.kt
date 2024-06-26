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

package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.validatables.Validatable
import dev.nesk.akkurate.validatables.validatableOf

private val emptyIterator = object : Iterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun next(): Nothing = throw NoSuchElementException("This iterator is empty")
}

/**
 * Returns an iterator over the elements of this object. Each element is wrapped with a [Validatable].
 */
public operator fun <T> Validatable<Iterable<T>?>.iterator(): Iterator<Validatable<T>> = unwrap()?.let { iterable ->
    iterable
        .asSequence()
        .withIndex()
        .map { Validatable(it.value, it.index.toString(), this) }
        .iterator()
} ?: emptyIterator

/**
 * Returns the first element, wrapped in [Validatable].
 *
 * The wrapped value is `null` if the collection is empty.
 */
public fun <T> Validatable<Iterable<T>?>.first(): Validatable<T?> = try {
    validatableOf(Iterable<T>::first)
} catch (e: NoSuchElementException) {
    Validatable(null, "first", this)
}

/**
 * Returns the last element, wrapped in [Validatable].
 *
 * The wrapped value is `null` if the collection is empty.
 */
public fun <T> Validatable<Iterable<T>?>.last(): Validatable<T?> = try {
    validatableOf(Iterable<T>::last)
} catch (e: NoSuchElementException) {
    Validatable(null, "last", this)
}

/**
 * Iterates over each element of this object and wraps them with a [Validatable] before passing them to the [block].
 *
 * ```
 * Validator<List<String>> {
 *     // Validate that each string is not empty
 *     each { isNotEmpty() }
 * }
 * ```
 */
public inline fun <T> Validatable<Iterable<T>?>.each(block: Validatable<T>.() -> Unit) {
    for (row in this) row.invoke(block)
}
