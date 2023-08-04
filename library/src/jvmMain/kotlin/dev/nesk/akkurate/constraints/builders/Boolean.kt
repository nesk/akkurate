package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

public fun Validatable<Boolean?>.isTrue(): Constraint = constrain { it == true } otherwise { "Must be true" }
public fun Validatable<Boolean?>.isNotTrue(): Constraint = constrain { it != true } otherwise { "Must not be true" }

public fun Validatable<Boolean?>.isFalse(): Constraint = constrain { it == false } otherwise { "Must be false" }
public fun Validatable<Boolean?>.isNotFalse(): Constraint = constrain { it != false } otherwise { "Must not be false" }
