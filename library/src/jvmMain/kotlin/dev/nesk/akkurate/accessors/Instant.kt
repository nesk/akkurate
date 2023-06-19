package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.validatables.Validatable
import dev.nesk.akkurate.validatables.validatableOf
import java.time.Instant

public val Validatable<Instant>.epochSecond: Validatable<Long> get() = validatableOf(Instant::getEpochSecond)
public val Validatable<Instant>.nanos: Validatable<Int> get() = validatableOf(Instant::getNano)
