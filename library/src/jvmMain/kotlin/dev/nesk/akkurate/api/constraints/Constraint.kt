package dev.nesk.akkurate.api.constraints

public interface Constraint {
    public val satisfied: Boolean
    public operator fun component1(): Boolean = satisfied

    public var message: String?
    public var path: List<String>?
}

public infix fun Constraint.explain(message: String): Constraint = apply { this.message = message }
public infix fun Constraint.withPath(path: List<String>): Constraint = apply { this.path = path }
public infix fun Constraint.withPath(fragment: String): Constraint = apply { path = listOf(fragment) }
