package dev.nesk.akkurate.api.constraints.builders

import dev.nesk.akkurate.api.Validatable
import dev.nesk.akkurate.api.constraints.Constraint

inline fun <T> Validatable<out T>.constrain(block: (T) -> Boolean): Constraint<T> = TODO()
