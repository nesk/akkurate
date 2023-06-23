package dev.nesk.akkurate

public sealed interface ValidationErrors : Set<ValidationErrors.PathAndMessage> {
    public val byPath: Map<Path, Messages>

    public sealed interface PathAndMessage {
        public val path: Path
        public operator fun component1(): Path
        public val message: String
        public operator fun component2(): String
    }

    public sealed interface Messages : Set<String>
}
