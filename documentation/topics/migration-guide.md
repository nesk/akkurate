# Migration guide

Some breaking changes might happen sometimes, especially until %product% reaches its first stable version. Here you can
find how to migrate to a new version containing breaking changes.

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
