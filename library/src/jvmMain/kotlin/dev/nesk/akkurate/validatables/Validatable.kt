package dev.nesk.akkurate.validatables

import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty1

public class Validatable<T> internal constructor(private val wrappedValue: T, pathSegment: String? = null, parent: Validatable<*>? = null) {
    private val path: List<String> = buildList {
        addAll(parent?.path ?: emptyList())
        if (!pathSegment.isNullOrEmpty()) {
            add(pathSegment)
        }
    }

    public fun path(): List<String> = path

    public fun unwrap(): T = wrappedValue
}

public inline operator fun <T> Validatable<T>.invoke(block: Validatable<T>.() -> Unit): Unit = this.block()

public fun <T : Any, V> Validatable<T>.validatableOf(getter: KProperty1<T, V>): Validatable<V> {
    return Validatable(getter.get(unwrap()), getter.name, this)
}

@JvmName("nullableValidatableOfProperty")
public fun <T : Any?, V> Validatable<T>.validatableOf(getter: KProperty1<T & Any, V>): Validatable<V?> {
    return Validatable(unwrap()?.let { getter.get(it) }, getter.name, this)
}

public fun <T : Any, V> Validatable<T>.validatableOf(getter: KFunction1<T, V>): Validatable<V> {
    return Validatable(getter.invoke(unwrap()), getter.name, this)
}

@JvmName("nullableValidatableOfFunction")
public fun <T : Any?, V> Validatable<T>.validatableOf(getter: KFunction1<T & Any, V>): Validatable<V?> {
    return Validatable(unwrap()?.let { getter.invoke(it) }, getter.name, this)
}
