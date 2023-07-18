package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.validatables.Validatable
import dev.nesk.akkurate.validatables.validatableOf

public val Validatable<String>.length: Validatable<Int> get() = validatableOf(String::length)
