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

package dev.nesk.akkurate.ktor.client

import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.constrain
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertIs

class ClientValidatorTestJvm {
    @Test
    fun runner_throws_when_the_validation_fails(): Unit = runBlocking {
        val exception = assertFails { runner.validate("") }
        assertIs<ValidationResult.Exception>(exception)
    }

    @Test
    fun runner_ignores_validation_on_instance_mismatch(): Unit = runBlocking {
        runner.validate(object {})
    }

    @Test
    fun contextual_runner_throws_when_the_validation_fails(): Unit = runBlocking {
        val exception = assertFails { contextualRunner.validate("") }
        assertIs<ValidationResult.Exception>(exception)
    }

    @Test
    fun contextual_runner_ignores_validation_on_instance_mismatch(): Unit = runBlocking {
        contextualRunner.validate(object {})
    }

    @Test
    fun suspendable_runner_throws_when_the_validation_fails(): Unit = runBlocking {
        val exception = assertFails { suspendableRunner.validate("") }
        assertIs<ValidationResult.Exception>(exception)
    }

    @Test
    fun suspendable_runner_ignores_validation_on_instance_mismatch(): Unit = runBlocking {
        suspendableRunner.validate(object {})
    }

    @Test
    fun suspendable_contextual_runner_throws_when_the_validation_fails(): Unit = runBlocking {
        val exception = assertFails { suspendableContextualRunner.validate("") }
        assertIs<ValidationResult.Exception>(exception)
    }

    @Test
    fun suspendable_contextual_runner_ignores_validation_on_instance_mismatch(): Unit = runBlocking {
        suspendableContextualRunner.validate(object {})
    }

    private companion object {
        val runner = ClientValidator.Runner(
            String::class,
            Validator<String> { constrain { false } }
        )

        val contextualRunner = ClientValidator.Runner.WithContext(
            String::class,
            Validator<Boolean, String> { ctx -> constrain { ctx } }
        ) { false }

        val suspendableRunner = ClientValidator.SuspendableRunner(
            String::class,
            Validator.suspendable<String> { constrain { false } }
        )

        val suspendableContextualRunner = ClientValidator.SuspendableRunner.WithContext(
            String::class,
            Validator.suspendable<Boolean, String> { ctx -> constrain { ctx } }
        ) { false }
    }
}
