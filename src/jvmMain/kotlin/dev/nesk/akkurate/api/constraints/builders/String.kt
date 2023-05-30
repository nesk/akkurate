package dev.nesk.akkurate.api.constraints.builders

import dev.nesk.akkurate.api.Validatable
import dev.nesk.akkurate.api.constraints.Constraint

fun Validatable<String>.empty(): Constraint<String> = TODO()
fun Validatable<String>.notEmpty(): Constraint<String> = TODO()
fun Validatable<String>.minLength(length: Int): Constraint<String> = TODO()
fun Validatable<String>.maxLength(length: Int): Constraint<String> = TODO()
