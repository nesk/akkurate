package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.validatables.Validatable

public fun <T> Validatable<T?>.notNull(): Constraint = constrain { it != null }
