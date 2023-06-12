package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.Validatable
import dev.nesk.akkurate.constrain
import dev.nesk.akkurate.constraints.Constraint

public fun <T> Validatable<T?>.notNull(): Constraint = constrain { it != null }
