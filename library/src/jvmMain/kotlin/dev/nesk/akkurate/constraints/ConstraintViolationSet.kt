package dev.nesk.akkurate.constraints

import dev.nesk.akkurate.Path

public class ConstraintViolationSet internal constructor(private val messages: Set<ConstraintViolation>) : Set<ConstraintViolation> by messages {
    public val byPath: Map<Path, Set<ConstraintViolation>> by lazy { messages.groupBy { it.path }.mapValues { it.value.toSet() } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConstraintViolationSet

        return messages == other.messages
    }

    override fun hashCode(): Int = messages.hashCode()

    override fun toString(): String = "ConstraintViolationSet(errors=$messages)"

}
