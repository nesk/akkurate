# Migration guide

Some breaking changes might happen sometimes, especially until %product% reaches its first stable version. Here you can
find how to migrate to a new version containing breaking changes.

## Version [unreleased]

### `ConstraintViolationSet.equals` is now symmetric

[The
`ConstraintViolationSet` class](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.constraints/-constraint-violation-set/index.html)
implements [the `Set` interface,](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/) but its
implementation of the `equals` method was broken because it wasn't
symmetric, [like required by the specification.](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/equals.html)

This release fixes the bug, but it brings a breaking change:

<tabs>
<tab title="Before v[unreleased]">

```kotlin
fun compare(
    standardSet: Set<ConstraintViolation>,
    constraintViolationSet: ConstraintViolationSet
) {
    standardSet.equals(constraintViolationSet) // ✅ true
    constraintViolationSet.equals(standardSet) // ❌ false
}
```

</tab>
<tab title="After v[unreleased]">

```kotlin
fun compare(
    standardSet: Set<ConstraintViolation>,
    constraintViolationSet: ConstraintViolationSet
) {
    standardSet.equals(constraintViolationSet) // ✅ true
    constraintViolationSet.equals(standardSet) // ✅ true
}
```

</tab>
</tabs>

### The `Validate` annotation is now part of `akkurate-core`

[The `Validate` annotation](https://akkurate.dev/api/akkurate-core/dev.nesk.akkurate.annotations/-validate/index.html)
has been moved from the `akkurate-ksp-plugin` artifact to the `akkurate-core` one.

This is not a breaking change, but it means you can remove the `implementation` dependency to
`dev.nesk.akkurate:akkurate-ksp-plugin` in your Gradle configuration:

<tabs>
<tab title="Before v[unreleased]">

```kotlin
dependencies {
    implementation("dev.nesk.akkurate:akkurate-core:0.8.0")
    implementation("dev.nesk.akkurate:akkurate-ksp-plugin:0.8.0")
    ksp("dev.nesk.akkurate:akkurate-ksp-plugin:0.8.0")
}
```

</tab>
<tab title="After v[unreleased]">

```kotlin
dependencies {
    implementation("dev.nesk.akkurate:akkurate-core:[unreleased]")
    ksp("dev.nesk.akkurate:akkurate-ksp-plugin:[unreleased]")
}
```

</tab>
</tabs>

## Version 0.7.0

Akkurate improved its DSL by
implementing [scope control,](https://kotlinlang.org/docs/type-safe-builders.html#scope-control-dslmarker) which means
you can no longer implicitly reference a declaration of an outer receiver:

<tabs>
<tab title="With implicit receivers">

```kotlin
@Validate
data class Book(val title: String, val labels: List<String>)

val validateBook = Validator<Book> {
    title.isNotBlank()

    labels {
        each {
            isNotBlank()

            hasSizeLowerThan(10)
            // ❌ Compiler error: 'hasSizeLowerThan' can't be called
            // in this context by implicit receiver. Use the explicit
            // one if necessary.
            // 💬 It happens because 'hasSizeLowerThan' is implicitly
            // applied to the 'labels' property.
        }
    }
}
```

</tab>
<tab title="With explicit receivers">

```kotlin
@Validate
data class Book(val title: String, val labels: List<String>)

val validateBook = Validator<Book> {
    title.isNotBlank()

    labels {
        hasSizeLowerThan(10)
        // ✅ Success: 'hasSizeLowerThan' is explicitly applied to the
        // 'labels' property.

        each {
            isNotBlank()
        }
    }
}
```

</tab>
</tabs>

## Version 0.4.0

The `Configuration`
declaration [is no longer a data class.](https://kotlinlang.org/docs/jvm-api-guidelines-backward-compatibility.html#don-t-use-data-classes-in-an-api)
To create a new configuration, use the following builder DSL:

<tabs>
<tab title="After v0.4.0">

```kotlin
Configuration {
    defaultViolationMessage = "The field contains an invalid value"
    rootPath("custom", "root", "path")
}
```

</tab>
<tab title="Before v0.4.0">

```kotlin
Configuration(
    defaultViolationMessage = "The field contains an invalid value",
    rootPath = listOf("custom", "root", "path"),
)
```

</tab>
</tabs>
