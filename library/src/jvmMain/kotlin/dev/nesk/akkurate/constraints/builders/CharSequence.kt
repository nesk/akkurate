package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

public fun Validatable<CharSequence?>.empty(): Constraint =
    constrainIfNotNull { it.isEmpty() } otherwise { "Must be empty" }

public fun Validatable<CharSequence?>.notEmpty(): Constraint =
    constrainIfNotNull { it.isNotEmpty() } otherwise { "Must not be empty" }

public fun Validatable<CharSequence?>.lengthLowerThan(length: Int): Constraint =
    constrainIfNotNull { it.length < length } otherwise { "Length must be lower than $length" }

public fun Validatable<CharSequence?>.lengthLowerThanOrEqualTo(length: Int): Constraint =
    constrainIfNotNull { it.length <= length } otherwise { "Length must be lower than or equal to $length" }

public fun Validatable<CharSequence?>.lengthGreaterThan(length: Int): Constraint =
    constrainIfNotNull { it.length > length } otherwise { "Length must be greater than $length" }

public fun Validatable<CharSequence?>.lengthGreaterThanOrEqualTo(length: Int): Constraint =
    constrainIfNotNull { it.length >= length } otherwise { "Length must be greater than or equal to $length" }

public fun Validatable<CharSequence?>.lengthBetween(range: IntRange): Constraint =
    constrainIfNotNull { it.length in range } otherwise { "Length must be between ${range.first} and ${range.last}" }

public fun Validatable<CharSequence?>.startingWith(prefix: CharSequence): Constraint =
    constrainIfNotNull { it.startsWith(prefix) } otherwise { "Must start with \"$prefix\"" }

public fun Validatable<CharSequence?>.notStartingWith(prefix: CharSequence): Constraint =
    constrainIfNotNull { !it.startsWith(prefix) } otherwise { "Must not start with \"$prefix\"" }

public fun Validatable<CharSequence?>.endingWith(suffix: CharSequence): Constraint =
    constrainIfNotNull { it.endsWith(suffix) } otherwise { "Must end with \"$suffix\"" }

public fun Validatable<CharSequence?>.notEndingWith(suffix: CharSequence): Constraint =
    constrainIfNotNull { !it.endsWith(suffix) } otherwise { "Must not end with \"$suffix\"" }

public fun Validatable<CharSequence?>.containing(other: CharSequence): Constraint =
    constrainIfNotNull { it.contains(other) } otherwise { "Must contain \"$other\"" }

public fun Validatable<CharSequence?>.notContaining(other: CharSequence): Constraint =
    constrainIfNotNull { !it.contains(other) } otherwise { "Must not contain \"$other\"" }
