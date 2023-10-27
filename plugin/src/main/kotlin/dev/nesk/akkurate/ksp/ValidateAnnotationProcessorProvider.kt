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

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import dev.nesk.akkurate.annotations.ExperimentalAkkurateCompilerApi

@ExperimentalAkkurateCompilerApi
public class ValidateAnnotationProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val options = environment.options
        val config = ValidateAnnotationProcessorConfig().apply {
            options["appendPackagesWith"]?.let { appendPackagesWith = it }
            options["__PRIVATE_API__prependPackagesWith"]?.let { prependPackagesWith = it }
            validatableClasses = options.getOrDefault("validatableClasses", "").split("|").toSet()
            validatablePackages = options.getOrDefault("__PRIVATE_API__validatablePackages", "").split("|").toSet()
        }
        return ValidateAnnotationProcessor(environment.codeGenerator, environment.logger, config)
    }
}
