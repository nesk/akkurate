/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nesk.akkurate

import dev.nesk.akkurate.constraints.otherwise

/**
 * Common interface between [Configuration] and [Configuration.Builder].
 *
 * Acts as a single source of documentation for the properties
 */
private interface ConfigurationInterface {
    /**
     * The default message for failed constraints.
     *
     * Built-in constraints always override the default message:
     * ```
     * Validator<String> {
     *     isNotEmpty() // Message: Must not be empty
     * }
     * ```
     *
     * However, inline constraints use the default message if not overriden:
     * ```
     * Validator<Any> {
     *     constrain { false } // Message: The value is invalid
     * }
     *```
     *
     * You can always override the message by using [otherwise].
     */
    val defaultViolationMessage: String

    /**
     * A root path prefixing all paths available in constraint violations.
     *
     * By default, the root path is empty:
     * ```
     * @Validate
     * data class Book(val title: String)
     *
     * Validator<Book> {
     *     title.length.isGreaterThan(0) // Path: [title, length]
     * }
     * ```
     *
     * When non-empty, the root path is prefixing all the paths:
     * ```
     * val config = Configuration {
     *     rootPath("book")
     * }
     *
     * Validator<Book>(config) {
     *     title.length.isGreaterThan(0) // Path: [book, title, length]
     * }
     * ```
     */
    val rootPath: Path

    /**
     * Whether validations should fail immediately on the first constraint violation, or wait for all the constraints to execute.
     *
     * When set to `true`, the first violation constraint will halt the validation, and [a failure][ValidationResult.Failure]
     * will be returned immediately without executing the upcoming constraints:
     * ```
     * val config = Configuration {
     *     failOnFirstViolation = true
     * }
     * val validate = Validator<Any?>(config) {
     *     constrain { false } otherwise { "first message" }
     *     constrain { false } otherwise { "second message" }
     * }
     * val failure = validate(null) as ValidationResult.Failure
     * failure.violations.size // Returns: 1
     * failure.violations.single().message // Returns: "first message"
     * ```
     *
     * When set to `false` (the default), all the constraints will be executed before returning [a result][ValidationResult]:
     *
     * ```
     * val config = Configuration {
     *     failOnFirstViolation = false
     * }
     * val validate = Validator<Any?>(config) {
     *     constrain { false } otherwise { "first message" }
     *     constrain { false } otherwise { "second message" }
     * }
     * val failure = validate(null) as ValidationResult.Failure
     * failure.violations.size // Returns: 2
     * ```
     */
    val failOnFirstViolation: Boolean
}

/**
 * Configuration of the [Validator] instance.
 *
 * Can be used to adjust some behaviors of the validator, and the validations it performs.
 *
 * Instantiate and adjust it with the [Configuration { ... }][Configuration.invoke] factory function.
 */
public class Configuration private constructor() : ConfigurationInterface {
    public companion object {
        /**
         * Creates an instance of [Configuration] configured from the optionally given [Configuration instance][from] and adjusted with [builderAction].
         */
        public operator fun invoke(from: Configuration = Configuration(), builderAction: Builder.() -> Unit = {}): Configuration =
            Builder(from).also(builderAction).build()
    }

    /**
     * Builder of the [Configuration] instance provided by [Configuration { ... }][Configuration.invoke] factory function.
     */
    public class Builder internal constructor(from: Configuration) : ConfigurationInterface {
        private val config = from.copy()

        public override var defaultViolationMessage: String by config::defaultViolationMessage

        public override var rootPath: Path by config::rootPath

        public override var failOnFirstViolation: Boolean by config::failOnFirstViolation

        /**
         * Defines the root path.
         */
        public fun rootPath(vararg pathSegments: String) {
            rootPath = pathSegments.toList()
        }

        internal fun build(): Configuration = config.copy()
    }

    public override var defaultViolationMessage: String = "The value is invalid."
        private set

    public override var rootPath: Path = emptyList()
        private set

    public override var failOnFirstViolation: Boolean = false
        private set

    private fun copy() = Configuration().also { to ->
        to.defaultViolationMessage = defaultViolationMessage
        to.rootPath = rootPath
        to.failOnFirstViolation = failOnFirstViolation
    }

    //region equals/hashCode/toString
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Configuration

        if (defaultViolationMessage != other.defaultViolationMessage) return false
        if (rootPath != other.rootPath) return false
        if (failOnFirstViolation != other.failOnFirstViolation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = defaultViolationMessage.hashCode()
        result = 31 * result + rootPath.hashCode()
        result = 31 * result + failOnFirstViolation.hashCode()
        return result
    }

    override fun toString(): String =
        "Configuration(defaultViolationMessage='$defaultViolationMessage', rootPath=$rootPath, failOnFirstViolation=$failOnFirstViolation)"
    //endregion
}
