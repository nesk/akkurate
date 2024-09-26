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

package dev.nesk.akkurate.ksp

import dev.nesk.akkurate.annotations.ExperimentalAkkurateCompilerApi

@ExperimentalAkkurateCompilerApi
public data class ValidateAnnotationProcessorConfig(
    /**
     * Appends the provided value to the original package of the generated accessor.
     *
     * In the following example, the classes inside `com.example.project` will generate accessors into `com.example.project.foo.bar`:
     * ```kotlin
     * appendPackagesWith = "foo.bar"
     * ```
     */
    var appendPackagesWith: String = "validation.accessors",

    /**
     * A collection of classes to generate accessors for. Use a Fully-Qualified Class Name (FQCN) for each class.
     *
     * Example:
     * ```kotlin
     * validatableClasses = setOf(
     *     "com.example.project.FirstClass",
     *     "com.example.project.SecondClass",
     * )
     * ```
     */
    var validatableClasses: Set<String> = emptySet(),
) {
    /**
     * A collection of packages containing classes to generate accessors for.
     *
     * Each class or interface contained within the package will be processed.
     *
     * This configuration
     *
     * Example:
     * ```kotlin
     * validatablePackages = setOf(
     *     "com.example.project1",
     *     "com.example.project2",
     * )
     * ```
     */
    internal var validatablePackages: Set<String> = emptySet()

    /**
     * Prepends the provided value to the original package of the generated accessor.
     *
     * In the following example, the classes inside `com.example.project` will generate accessors into `foo.bar.com.example.project`:
     * ```kotlin
     * prependPackagesWith = "foo.bar"
     * ```
     */
    internal var prependPackagesWith: String = ""

    private val String?.symbolNameOrNull: String?
        get() = this?.run {
            trim().trim { it == '.' }.ifEmpty { null }
        }

    internal val normalizedAppendPackagesWith get() = appendPackagesWith.symbolNameOrNull ?: ""

    internal val normalizedPrependPackagesWith get() = prependPackagesWith.symbolNameOrNull ?: ""

    internal val normalizedValidatableClasses get() = validatableClasses.mapNotNull { it.symbolNameOrNull }.toSet()

    internal val normalizedValidatablePackages get() = validatablePackages.mapNotNull { it.symbolNameOrNull }.toSet()
}
