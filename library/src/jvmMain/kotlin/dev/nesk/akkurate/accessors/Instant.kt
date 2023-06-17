package dev.nesk.akkurate.accessors

import dev.nesk.akkurate.validatables.Validatable
import java.time.Instant

public val Validatable<Instant>.epochSeconds: Validatable<Long> get() = createValidatable(unwrap().epochSecond)
public val Validatable<Instant>.nanos: Validatable<Int> get() = createValidatable(unwrap().nano)
