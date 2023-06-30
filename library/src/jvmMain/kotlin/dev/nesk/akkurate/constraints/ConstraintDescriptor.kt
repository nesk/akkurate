package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Path

public sealed interface ConstraintDescriptor {
    public val message: String
    public val path: Path
}
