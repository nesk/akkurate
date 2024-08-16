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

package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

public fun Validatable<CharSequence?>.isEmpty(): Constraint =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

public fun Validatable<CharSequence?>.isNotEmpty(): Constraint =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

public fun Validatable<CharSequence?>.isBlank(): Constraint =
    constrainIfNotNull { it.isBlank() } otherwise { "Must be blank" }

public fun Validatable<CharSequence?>.isNotBlank(): Constraint =
    constrainIfNotNull { it.isNotBlank() } otherwise { "Must not be blank" }

public fun Validatable<CharSequence?>.hasLengthEqualTo(length: Int): Constraint =
    constrainIfNotNull { it.length == length } otherwise { "Length must be equal to $length" }

public fun Validatable<CharSequence?>.hasLengthNotEqualTo(length: Int): Constraint =
    constrainIfNotNull { it.length != length } otherwise { "Length must be different from $length" }

public fun Validatable<CharSequence?>.hasLengthLowerThan(length: Int): Constraint =
    constrainIfNotNull { it.length < length } otherwise { "Length must be lower than $length" }

public fun Validatable<CharSequence?>.hasLengthLowerThanOrEqualTo(length: Int): Constraint =
    constrainIfNotNull { it.length <= length } otherwise { "Length must be lower than or equal to $length" }

public fun Validatable<CharSequence?>.hasLengthGreaterThan(length: Int): Constraint =
    constrainIfNotNull { it.length > length } otherwise { "Length must be greater than $length" }

public fun Validatable<CharSequence?>.hasLengthGreaterThanOrEqualTo(length: Int): Constraint =
    constrainIfNotNull { it.length >= length } otherwise { "Length must be greater than or equal to $length" }

public fun Validatable<CharSequence?>.hasLengthBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.length in range } otherwise { "Length must be between ${range.first} and ${range.last}" }

public fun Validatable<CharSequence?>.isMatching(regex: Regex): Constraint =
    constrainIfNotNull { regex.matches(it) } otherwise { "Must match the following pattern: ${regex.pattern}" }

public fun Validatable<CharSequence?>.isNotMatching(regex: Regex): Constraint =
    constrainIfNotNull { !regex.matches(it) } otherwise { "Must not match the following pattern: ${regex.pattern}" }

public fun Validatable<CharSequence?>.isStartingWith(prefix: CharSequence): Constraint =
    constrainIfNotNull { it.startsWith(prefix) } otherwise { "Must start with \"$prefix\"" }

public fun Validatable<CharSequence?>.isNotStartingWith(prefix: CharSequence): Constraint =
    constrainIfNotNull { !it.startsWith(prefix) } otherwise { "Must not start with \"$prefix\"" }

public fun Validatable<CharSequence?>.isEndingWith(suffix: CharSequence): Constraint =
    constrainIfNotNull { it.endsWith(suffix) } otherwise { "Must end with \"$suffix\"" }

public fun Validatable<CharSequence?>.isNotEndingWith(suffix: CharSequence): Constraint =
    constrainIfNotNull { !it.endsWith(suffix) } otherwise { "Must not end with \"$suffix\"" }

public fun Validatable<CharSequence?>.isContaining(other: CharSequence): Constraint =
    constrainIfNotNull { it.contains(other) } otherwise { "Must contain \"$other\"" }

public fun Validatable<CharSequence?>.isNotContaining(other: CharSequence): Constraint =
    constrainIfNotNull { !it.contains(other) } otherwise { "Must not contain \"$other\"" }
