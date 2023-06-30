package dev.nesk.akkurate

import dev.nesk.akkurate.validatables.Validatable

public typealias Path = List<String>
public typealias MutablePath = MutableList<String>

public class PathBuilder(private val validatable: Validatable<*>) {
    public fun absolute(vararg pathSegments: String): Path = pathSegments.toList()

    public fun relative(vararg pathSegments: String): Path {
        val parentPath = validatable.parent?.path() ?: emptyList()
        return parentPath + pathSegments.toList()
    }

    public fun appended(vararg pathSegments: String): Path = validatable.path() + pathSegments.toList()
}

public fun Validatable<*>.path(block: PathBuilder.() -> Path): Path = PathBuilder(this).block()
