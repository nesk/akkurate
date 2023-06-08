package dev.nesk.akkurate.api.constraints.builders

import dev.nesk.akkurate.api.Validatable
import dev.nesk.akkurate.api.constrain
import dev.nesk.akkurate.api.constraints.Constraint

public fun Validatable<String>.empty(): Constraint = constrain { it.isEmpty() }
public fun Validatable<String>.notEmpty(): Constraint = constrain { it.isNotEmpty() }
public fun Validatable<String>.minLength(length: Int): Constraint = constrain { it.length >= length }
public fun Validatable<String>.maxLength(length: Int): Constraint = constrain { it.length <= length }
