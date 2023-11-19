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

package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate._test.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharSequenceTest {
    //region isEmpty
    @Test
    fun `'isEmpty' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isEmpty().satisfied)
    }

    @Test
    fun `'isEmpty' succeeds when the value is empty`() {
        assertTrue(Validatable("").isEmpty().satisfied)
    }

    @Test
    fun `'isEmpty' fails when the value is not empty`() {
        assertFalse(Validatable("foo").isEmpty().satisfied)
    }
    //endregion

    //region isNotEmpty
    @Test
    fun `'isNotEmpty' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isNotEmpty().satisfied)
    }

    @Test
    fun `'isNotEmpty' succeeds when the value is not empty`() {
        assertTrue(Validatable("foo").isNotEmpty().satisfied)
    }

    @Test
    fun `'isNotEmpty' fails when the value is empty`() {
        assertFalse(Validatable("").isNotEmpty().satisfied)
    }
    //endregion

    //region isBlank
    @Test
    fun `'isBlank' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isBlank().satisfied)
    }

    @Test
    fun `'isBlank' succeeds when the value is blank`() {
        assertTrue(Validatable("  \t\n").isBlank().satisfied)
    }

    @Test
    fun `'isBlank' fails when the value is not blank`() {
        assertFalse(Validatable("foo").isBlank().satisfied)
    }
    //endregion

    //region isNotBlank
    @Test
    fun `'isNotBlank' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isNotBlank().satisfied)
    }

    @Test
    fun `'isNotBlank' succeeds when the value is not blank`() {
        assertTrue(Validatable("foo").isNotBlank().satisfied)
    }

    @Test
    fun `'isNotBlank' fails when the value is blank`() {
        assertFalse(Validatable("  \t\n").isNotBlank().satisfied)
    }
    //endregion

    //region hasLengthEqualTo
    @Test
    fun `'hasLengthEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthEqualTo(0).satisfied)
    }

    @Test
    fun `'hasLengthEqualTo' succeeds when the length is equal to the provided one`() {
        assertTrue(Validatable("foo").hasLengthEqualTo(3).satisfied)
    }

    @Test
    fun `'hasLengthEqualTo' fails when the length is not equal to the provided one`() {
        assertFalse(Validatable("foo").hasLengthEqualTo(-1).satisfied)
    }
    //endregion

    //region hasLengthNotEqualTo
    @Test
    fun `'hasLengthNotEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthNotEqualTo(0).satisfied)
    }

    @Test
    fun `'hasLengthNotEqualTo' succeeds when the length is not equal to the provided one`() {
        assertTrue(Validatable("foo").hasLengthNotEqualTo(-1).satisfied)
    }

    @Test
    fun `'hasLengthNotEqualTo' fails when the length is equal to the provided one`() {
        assertFalse(Validatable("foo").hasLengthNotEqualTo(3).satisfied)
    }
    //endregion

    //region hasLengthLowerThan
    @Test
    fun `'hasLengthLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthLowerThan(0).satisfied)
    }

    @Test
    fun `'hasLengthLowerThan' succeeds when the length is lower than the provided one`() {
        assertTrue(Validatable("foo").hasLengthLowerThan(4).satisfied)
    }

    @Test
    fun `'hasLengthLowerThan' fails when the length is greater than or equal to the provided one`() {
        assertFalse(Validatable("foo").hasLengthLowerThan(2).satisfied, "The constraint is not satisfied when the length is greater than the provided one")
        assertFalse(Validatable("foo").hasLengthLowerThan(3).satisfied, "The constraint is not satisfied when the length is equal to the provided one")
    }
    //endregion

    //region hasLengthLowerThanOrEqualTo
    @Test
    fun `'hasLengthLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthLowerThanOrEqualTo(0).satisfied)
    }

    @Test
    fun `'hasLengthLowerThanOrEqualTo' succeeds when the length is lower than or equal to the provided one`() {
        assertTrue(Validatable("foo").hasLengthLowerThanOrEqualTo(4).satisfied, "The constraint is satisfied when the length is lower than the provided one")
        assertTrue(Validatable("foo").hasLengthLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the length is equal to the provided one")
    }

    @Test
    fun `'hasLengthLowerThanOrEqualTo' fails when the length is greater than the provided one`() {
        assertFalse(Validatable("foo").hasLengthLowerThanOrEqualTo(2).satisfied)
    }
    //endregion

    //region hasLengthGreaterThan
    @Test
    fun `'hasLengthGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthGreaterThan(0).satisfied)
    }

    @Test
    fun `'hasLengthGreaterThan' succeeds when the length is greater than the provided one`() {
        assertTrue(Validatable("foo").hasLengthGreaterThan(2).satisfied)
    }

    @Test
    fun `'hasLengthGreaterThan' fails when the length is lower than or equal to the provided one`() {
        assertFalse(Validatable("foo").hasLengthGreaterThan(4).satisfied, "The constraint is not satisfied when the length is lower than the provided one")
        assertFalse(Validatable("foo").hasLengthGreaterThan(3).satisfied, "The constraint is not satisfied when the length is equal to the provided one")
    }
    //endregion

    //region hasLengthGreaterThanOrEqualTo
    @Test
    fun `'hasLengthGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthGreaterThanOrEqualTo(0).satisfied)
    }

    @Test
    fun `'hasLengthGreaterThanOrEqualTo' succeeds when the length is greater than or equal to the provided one`() {
        assertTrue(Validatable("foo").hasLengthGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the length is greater than the provided one")
        assertTrue(Validatable("foo").hasLengthGreaterThanOrEqualTo(3).satisfied, "The constraint is satisfied when the length is equal to the provided one")
    }

    @Test
    fun `'hasLengthGreaterThanOrEqualTo' fails when the length is lower than the provided one`() {
        assertFalse(Validatable("foo").hasLengthGreaterThanOrEqualTo(4).satisfied)
    }
    //endregion

    //region hasLengthBetween
    @Test
    fun `'hasLengthBetween' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthBetween(-1..0).satisfied)
    }

    @Test
    fun `'hasLengthBetween' succeeds when the length is within the provided range`() {
        assertTrue(Validatable("foo").hasLengthBetween(2..4).satisfied)
    }

    @Test
    fun `'hasLengthBetween' fails when the length is outside the provided range`() {
        assertFalse(Validatable("foo").hasLengthBetween(1..2).satisfied)
    }
    //endregion

    //region isMatching
    @Test
    fun `'isMatching' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isMatching(Regex("")).satisfied)
    }

    @Test
    fun `'isMatching' succeeds when the value matches the provided regular expression`() {
        assertTrue(Validatable("foo").isMatching(Regex("^fo{2}$")).satisfied)
    }

    @Test
    fun `'isMatching' fails when the value doesn't match the provided regular expression`() {
        assertFalse(Validatable("foo").isMatching(Regex("^fo{1}$")).satisfied)
    }
    //endregion

    //region isNotMatching
    @Test
    fun `'isNotMatching' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isNotMatching(Regex("")).satisfied)
    }

    @Test
    fun `'isNotMatching' succeeds when the value matches the provided regular expression`() {
        assertTrue(Validatable("foo").isNotMatching(Regex("^fo{1}$")).satisfied)
    }

    @Test
    fun `'isNotMatching' fails when the value doesn't match the provided regular expression`() {
        assertFalse(Validatable("foo").isNotMatching(Regex("^fo{2}")).satisfied)
    }
    //endregion

    //region isStartingWith
    @Test
    fun `'isStartingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isStartingWith("").satisfied)
    }

    @Test
    fun `'isStartingWith' succeeds when the value starts with the provided char sequence`() {
        assertTrue(Validatable("foobar").isStartingWith("foo").satisfied)
    }

    @Test
    fun `'isStartingWith' fails when the value does not start with the provided char sequence`() {
        assertFalse(Validatable("bar").isStartingWith("foo").satisfied)
    }
    //endregion

    //region isNotStartingWith
    @Test
    fun `'isNotStartingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isNotStartingWith("").satisfied)
    }

    @Test
    fun `'isNotStartingWith' succeeds when the value does not start with the provided char sequence`() {
        assertTrue(Validatable("bar").isNotStartingWith("foo").satisfied)
    }

    @Test
    fun `'isNotStartingWith' fails when the value starts with the provided char sequence`() {
        assertFalse(Validatable("foobar").isNotStartingWith("foo").satisfied)
    }
    //endregion

    //region isEndingWith
    @Test
    fun `'isEndingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isEndingWith("").satisfied)
    }

    @Test
    fun `'isEndingWith' succeeds when the value ends with the provided char sequence`() {
        assertTrue(Validatable("foobar").isEndingWith("bar").satisfied)
    }

    @Test
    fun `'isEndingWith' fails when the value does not end with the provided char sequence`() {
        assertFalse(Validatable("foo").isEndingWith("bar").satisfied)
    }
    //endregion

    //region isNotEndingWith
    @Test
    fun `'isNotEndingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isNotEndingWith("").satisfied)
    }

    @Test
    fun `'isNotEndingWith' succeeds when the value does not end with the provided char sequence`() {
        assertTrue(Validatable("foo").isNotEndingWith("bar").satisfied)
    }

    @Test
    fun `'isNotEndingWith' fails when the value ends with the provided char sequence`() {
        assertFalse(Validatable("foobar").isNotEndingWith("bar").satisfied)
    }
    //endregion

    //region isContaining
    @Test
    fun `'isContaining' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isContaining("").satisfied)
    }

    @Test
    fun `'isContaining' succeeds when the value contains the provided char sequence`() {
        assertTrue(Validatable("foobarbaz").isContaining("bar").satisfied)
    }

    @Test
    fun `'isContaining' fails when the value does not contain the provided char sequence`() {
        assertFalse(Validatable("foobaz").isContaining("bar").satisfied)
    }
    //endregion

    //region isNotContaining
    @Test
    fun `'isNotContaining' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).isNotContaining("").satisfied)
    }

    @Test
    fun `'isNotContaining' succeeds when the value does not contain the provided char sequence`() {
        assertTrue(Validatable("foobaz").isNotContaining("bar").satisfied)
    }

    @Test
    fun `'isNotContaining' fails when the value contains the provided char sequence`() {
        assertFalse(Validatable("foobarbaz").isNotContaining("bar").satisfied)
    }
    //endregion
}
