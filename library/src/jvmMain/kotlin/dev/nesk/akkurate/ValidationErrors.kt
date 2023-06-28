package dev.nesk.akkurate

public class ValidationErrors internal constructor(private val messages: Set<Error>) : Set<ValidationErrors.Error> by messages {
    public val byPath: Map<Path, Set<Error>> by lazy { messages.groupBy { it.path }.mapValues { it.value.toSet() } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ValidationErrors

        return messages == other.messages
    }

    override fun hashCode(): Int = messages.hashCode()

    override fun toString(): String = "ValidationErrors(errors=$messages)"

    public class Error internal constructor(public val message: String, public val path: Path) {
        public operator fun component1(): String = message
        public operator fun component2(): Path = path

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Error

            if (message != other.message) return false
            return path == other.path
        }

        override fun hashCode(): Int {
            var result = message.hashCode()
            result = 31 * result + path.hashCode()
            return result
        }

        override fun toString(): String = "Error(message='$message', path=$path)"
    }
}
