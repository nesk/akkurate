package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.Validatable
import java.time.Instant

public val Validatable<Instant>.epochSeconds: Validatable<Long> get() = createValidatable(value.epochSecond)
public val Validatable<Instant>.nanos: Validatable<Int> get() = createValidatable(value.nano)
