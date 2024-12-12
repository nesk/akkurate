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

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.constrain
import kotlin.test.Test
import kotlin.test.assertEquals

class AkkurateConfigTest {
    @Test
    fun can_register_a_runner() {
        val config = AkkurateConfig()
        config.registerValidator(runner)
        assertEquals(1, config.validators.size)
    }

    @Test
    fun can_register_a_contextual_runner() {
        val config = AkkurateConfig()
        config.registerValidator(contextualRunner)
        assertEquals(1, config.validators.size)
    }

    @Test
    fun can_register_a_suspendable_runner() {
        val config = AkkurateConfig()
        config.registerValidator(suspendableRunner)
        assertEquals(1, config.validators.size)
    }

    @Test
    fun can_register_a_suspendable_contextual_runner() {
        val config = AkkurateConfig()
        config.registerValidator(suspendableContextualRunner)
        assertEquals(1, config.validators.size)
    }

    @Test
    fun can_register_multiple_runners() {
        val config = AkkurateConfig()
        config.registerValidator(runner)
        config.registerValidator(runner)
        assertEquals(2, config.validators.size)
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
