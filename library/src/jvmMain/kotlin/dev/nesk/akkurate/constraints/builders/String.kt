package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.Validatable
import dev.nesk.akkurate.constrainIfNotNull
import dev.nesk.akkurate.constraints.Constraint

// TODO: Need a prefix like "assert" for better natural reading?

public fun Validatable<out String?>.empty(): Constraint = constrainIfNotNull { it.isEmpty() }
public fun Validatable<out String?>.notEmpty(): Constraint = constrainIfNotNull { it.isNotEmpty() }
public fun Validatable<out String?>.minLength(length: Int): Constraint = constrainIfNotNull { it.length >= length }
public fun Validatable<out String?>.maxLength(length: Int): Constraint = constrainIfNotNull { it.length <= length }
