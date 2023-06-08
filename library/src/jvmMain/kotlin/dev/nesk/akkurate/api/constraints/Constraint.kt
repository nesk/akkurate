package dev.nesk.akkurate.api.constraints

interface Constraint {
    val satisfied: Boolean
    operator fun component1(): Boolean = satisfied

    var message: String?
    var path: List<String>?
}

infix fun Constraint.explain(message: String): Constraint = apply { this.message = message }
infix fun Constraint.withPath(path: List<String>) = apply { this.path = path }
infix fun Constraint.withPath(fragment: String) = apply { path = listOf(fragment) }
