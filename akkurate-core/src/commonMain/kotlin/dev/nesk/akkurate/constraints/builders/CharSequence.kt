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

import dev.nesk.akkurate.constraints.GenericConstraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.GenericValidatable

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isEmpty(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isNotEmpty(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isBlank(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isBlank() } otherwise { "Must be blank" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isNotBlank(): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.isNotBlank() } otherwise { "Must not be blank" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthEqualTo(length: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length == length } otherwise { "Length must be equal to $length" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthNotEqualTo(length: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length != length } otherwise { "Length must be different from $length" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthLowerThan(length: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length < length } otherwise { "Length must be lower than $length" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthLowerThanOrEqualTo(length: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length <= length } otherwise { "Length must be lower than or equal to $length" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthGreaterThan(length: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length > length } otherwise { "Length must be greater than $length" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthGreaterThanOrEqualTo(length: Int): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length >= length } otherwise { "Length must be greater than or equal to $length" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.hasLengthBetween(range: IntRange): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.length in range } otherwise { "Length must be between ${range.first} and ${range.last}" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isMatching(regex: Regex): GenericConstraint<MetadataType> =
    constrainIfNotNull { regex.matches(it) } otherwise { "Must match the following pattern: ${regex.pattern}" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isNotMatching(regex: Regex): GenericConstraint<MetadataType> =
    constrainIfNotNull { !regex.matches(it) } otherwise { "Must not match the following pattern: ${regex.pattern}" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isStartingWith(prefix: CharSequence): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.startsWith(prefix) } otherwise { "Must start with \"$prefix\"" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isNotStartingWith(prefix: CharSequence): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.startsWith(prefix) } otherwise { "Must not start with \"$prefix\"" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isEndingWith(suffix: CharSequence): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.endsWith(suffix) } otherwise { "Must end with \"$suffix\"" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isNotEndingWith(suffix: CharSequence): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.endsWith(suffix) } otherwise { "Must not end with \"$suffix\"" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isContaining(other: CharSequence): GenericConstraint<MetadataType> =
    constrainIfNotNull { it.contains(other) } otherwise { "Must contain \"$other\"" }

public fun <MetadataType> GenericValidatable<CharSequence?, MetadataType>.isNotContaining(other: CharSequence): GenericConstraint<MetadataType> =
    constrainIfNotNull { !it.contains(other) } otherwise { "Must not contain \"$other\"" }
