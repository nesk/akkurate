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

public data class ValidateAnnotationProcessorConfig(
    /**
     * Each accessor will be generated with a package composed of the original one appended with this value.
     *
     * In the following example, the classes inside `com.example.project` will generate accessors into `com.example.project.foo.bar`:
     * ```kotlin
     * appendPackagesWith = "foo.bar"
     * ```
     */
    var appendPackagesWith: String? = "validation.accessors",

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
    internal var overrideOriginalPackageWith: String? = null

    private val String?.symbolNameOrNull: String?
        get() = this?.run {
            trim().trim { it == '.' }.ifEmpty { null }
        }

    internal val normalizedOverrideOriginalPackageWith get() = overrideOriginalPackageWith.symbolNameOrNull

    internal val normalizedappendPackagesWith get() = appendPackagesWith.symbolNameOrNull

    internal val normalizedValidatableClasses get() = validatableClasses.mapNotNull { it.symbolNameOrNull }.toSet()
}
