package dev.nesk.akkurate.api.constraints.builders

import dev.nesk.akkurate.api.Validatable
import dev.nesk.akkurate.api.constraints.Constraint

public fun <T> Validatable<out Collection<T>>.minSize(length: Int): Constraint = TODO()
public fun <T> Validatable<out Collection<T>>.maxSize(length: Int): Constraint = TODO()
