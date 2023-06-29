package dev.nesk.akkurate

public data class Configuration(
    public val defaultViolationMessage: String = "The value is invalid.",
    public val rootPath: Path = emptyList(),
)
