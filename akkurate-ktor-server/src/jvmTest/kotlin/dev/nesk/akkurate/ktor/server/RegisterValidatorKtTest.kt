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

package dev.nesk.akkurate.ktor.server

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.constrain
import io.ktor.server.plugins.requestvalidation.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertIs
import kotlin.test.assertSame
import io.ktor.server.plugins.requestvalidation.Validator as KtorValidator

class RegisterValidatorKtTest {
    companion object {
        val context = object {}
        val value = object {}
        val validatorSlot = slot<KtorValidator>()
        val config = mockk<RequestValidationConfig>()

        init {
            every { config.validate(capture(validatorSlot)) } returns Unit
        }
    }

    //region registerValidator(sync_contextual_validator, context)
    @Test
    fun registered_contextual_validator_uses_the_provided_context_and_value() = runBlocking {
        val validate = Validator<Any, Any> { ctx -> constrain { ctx === context && it === value } }
        config.registerValidator(validate) { context }
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_contextual_validator_returns__ValidationResult_Valid__when_value_is_valid() = runBlocking {
        val validate = Validator<Any, Any> { constrain { true } }
        config.registerValidator(validate) { context }
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_contextual_validator_throws__ValidationResult_Exception__when_value_is_invalid(): Unit = runBlocking {
        val validate = Validator<Any, Any> { constrain { false } }
        config.registerValidator(validate) { context }
        val exception = assertFails { validatorSlot.captured.validate(value) }
        assertIs<dev.nesk.akkurate.ValidationResult.Exception>(exception)
    }
    //endregion

    //region registerValidator(sync_contextual_validator)
    @Test
    fun registered_validator_uses_the_provided_value() = runBlocking {
        val validate = Validator<Any> { constrain { it === value } }
        config.registerValidator(validate)
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_validator_returns__ValidationResult_Valid__when_value_is_valid() = runBlocking {
        val validate = Validator<Any> { constrain { true } }
        config.registerValidator(validate)
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_validator_throws__ValidationResult_Exception__when_value_is_invalid(): Unit = runBlocking {
        val validate = Validator<Any> { constrain { false } }
        config.registerValidator(validate)
        val exception = assertFails { validatorSlot.captured.validate(value) }
        assertIs<dev.nesk.akkurate.ValidationResult.Exception>(exception)
    }
    //endregion

    //region registerValidator(async_contextual_validator, context)
    @Test
    fun registered_async_contextual_validator_uses_the_provided_context_and_value() = runBlocking {
        val validate = Validator.suspendable<Any, Any> { ctx -> constrain { ctx === context && it === value } }
        config.registerValidator(validate) { context }
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_async_contextual_validator_returns__ValidationResult_Valid__when_value_is_valid() = runBlocking {
        val validate = Validator.suspendable<Any, Any> { constrain { true } }
        config.registerValidator(validate) { context }
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_async_contextual_validator_throws__ValidationResult_Exception__when_value_is_invalid(): Unit = runBlocking {
        val validate = Validator.suspendable<Any, Any> { constrain { false } }
        config.registerValidator(validate) { context }
        val exception = assertFails { validatorSlot.captured.validate(value) }
        assertIs<dev.nesk.akkurate.ValidationResult.Exception>(exception)
    }
    //endregion

    //region registerValidator(async_contextual_validator)
    @Test
    fun registered_async_validator_uses_the_provided_value() = runBlocking {
        val validate = Validator.suspendable<Any> { constrain { it === value } }
        config.registerValidator(validate)
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_async_validator_returns__ValidationResult_Valid__when_value_is_valid() = runBlocking {
        val validate = Validator.suspendable<Any> { constrain { true } }
        config.registerValidator(validate)
        val result = validatorSlot.captured.validate(value)
        assertSame(ValidationResult.Valid, result)
    }

    @Test
    fun registered_async_validator_throws__ValidationResult_Exception__when_value_is_invalid(): Unit = runBlocking {
        val validate = Validator.suspendable<Any> { constrain { false } }
        config.registerValidator(validate)
        val exception = assertFails { validatorSlot.captured.validate(value) }
        assertIs<dev.nesk.akkurate.ValidationResult.Exception>(exception)
    }
    //endregion
}
