package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

public fun Validatable<CharSequence?>.isEmpty(): Constraint =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

public fun Validatable<CharSequence?>.isNotEmpty(): Constraint =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

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
