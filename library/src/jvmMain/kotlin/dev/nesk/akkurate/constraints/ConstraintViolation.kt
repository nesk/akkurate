package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Path

public class ConstraintViolation internal constructor(public override val message: String, public override val path: Path) : ConstraintDescriptor {
    public operator fun component1(): String = message
    public operator fun component2(): Path = path

    internal fun copy(path: Path = this.path) = ConstraintViolation(message, path)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConstraintViolation

        if (message != other.message) return false
        return path == other.path
    }

    override fun hashCode(): Int {
        var result = message.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }

    override fun toString(): String = "ConstraintViolation(message='$message', path=$path)"
}
