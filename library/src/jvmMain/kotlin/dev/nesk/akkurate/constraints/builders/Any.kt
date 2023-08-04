package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

public fun <T> Validatable<T?>.isNull(): Constraint = constrain { it == null } otherwise { "Must be null" }
public fun <T> Validatable<T?>.isNotNull(): Constraint = constrain { it != null } otherwise { "Must not be null" }

public fun <T> Validatable<T?>.equalTo(other: T?): Constraint = constrain { it == other } otherwise { "Must be equal to \"$other\"" }
public fun <T> Validatable<T?>.equalTo(other: Validatable<T>): Constraint = constrain { it == other.unwrap() } otherwise { "Must be equal to \"${other.unwrap()}\"" }

public fun <T> Validatable<T?>.notEqualTo(other: T?): Constraint = constrain { it != other } otherwise { "Must be different from \"$other\"" }
public fun <T> Validatable<T?>.notEqualTo(other: Validatable<T>): Constraint = constrain { it != other.unwrap() } otherwise { "Must be different from \"${other.unwrap()}\"" }

public fun <T> Validatable<T?>.identicalTo(other: T?): Constraint = constrain { it === other } otherwise { "Must be identical to \"$other\"" }
public fun <T> Validatable<T?>.notIdenticalTo(other: T?): Constraint = constrain { it !== other } otherwise { "Must not be identical to \"$other\"" }
