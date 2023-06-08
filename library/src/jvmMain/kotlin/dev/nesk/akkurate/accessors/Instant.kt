package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.Validatable
import java.time.Instant

public val Validatable<Instant>.epochSeconds: Validatable<Long> get() = getValidatableValue(Instant::getEpochSecond)
public val Validatable<Instant>.nanos: Validatable<Int> get() = getValidatableValue(Instant::getNano)
