package dev.nesk.akkurate.api.constraints.builders

import dev.nesk.akkurate.api.Validatable
import dev.nesk.akkurate.api.constrain

fun Validatable<String>.empty() = constrain { it.isEmpty() }
fun Validatable<String>.notEmpty() = constrain { it.isNotEmpty() }
fun Validatable<String>.minLength(length: Int) = constrain { it.length >= length }
fun Validatable<String>.maxLength(length: Int) = constrain { it.length <= length }
