package dev.nesk.akkurate.api.constraints.builders

import dev.nesk.akkurate.api.Validatable
import dev.nesk.akkurate.api.constraints.Constraint

fun <T> Validatable<out Collection<T>>.minSize(length: Int): Constraint = TODO()
fun <T> Validatable<out Collection<T>>.maxSize(length: Int): Constraint = TODO()
