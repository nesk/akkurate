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

import dev.nesk.akkurate.test.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharSequenceTest {
    //region isEmpty
    @Test
    fun __isEmpty__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isEmpty().satisfied)
    }

    @Test
    fun __isEmpty__succeeds_when_the_value_is_empty() {
        assertTrue(Validatable("").isEmpty().satisfied)
    }

    @Test
    fun __isEmpty__fails_when_the_value_is_not_empty() {
        assertFalse(Validatable("foo").isEmpty().satisfied)
    }
    //endregion

    //region isNotEmpty
    @Test
    fun __isNotEmpty__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isNotEmpty().satisfied)
    }

    @Test
    fun __isNotEmpty__succeeds_when_the_value_is_not_empty() {
        assertTrue(Validatable("foo").isNotEmpty().satisfied)
    }

    @Test
    fun __isNotEmpty__fails_when_the_value_is_empty() {
        assertFalse(Validatable("").isNotEmpty().satisfied)
    }
    //endregion

    //region isBlank
    @Test
    fun __isBlank__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isBlank().satisfied)
    }

    @Test
    fun __isBlank__succeeds_when_the_value_is_blank() {
        assertTrue(Validatable("  \t\n").isBlank().satisfied)
    }

    @Test
    fun __isBlank__fails_when_the_value_is_not_blank() {
        assertFalse(Validatable("foo").isBlank().satisfied)
    }
    //endregion

    //region isNotBlank
    @Test
    fun __isNotBlank__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isNotBlank().satisfied)
    }

    @Test
    fun __isNotBlank__succeeds_when_the_value_is_not_blank() {
        assertTrue(Validatable("foo").isNotBlank().satisfied)
    }

    @Test
    fun __isNotBlank__fails_when_the_value_is_blank() {
        assertFalse(Validatable("  \t\n").isNotBlank().satisfied)
    }
    //endregion

    //region hasLengthEqualTo
    @Test
    fun __hasLengthEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthEqualTo(0).satisfied)
    }

    @Test
    fun __hasLengthEqualTo__succeeds_when_the_length_is_equal_to_the_provided_one() {
        assertTrue(Validatable("foo").hasLengthEqualTo(3).satisfied)
    }

    @Test
    fun __hasLengthEqualTo__fails_when_the_length_is_not_equal_to_the_provided_one() {
        assertFalse(Validatable("foo").hasLengthEqualTo(-1).satisfied)
    }
    //endregion

    //region hasLengthNotEqualTo
    @Test
    fun __hasLengthNotEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthNotEqualTo(0).satisfied)
    }

    @Test
    fun __hasLengthNotEqualTo__succeeds_when_the_length_is_not_equal_to_the_provided_one() {
        assertTrue(Validatable("foo").hasLengthNotEqualTo(-1).satisfied)
    }

    @Test
    fun __hasLengthNotEqualTo__fails_when_the_length_is_equal_to_the_provided_one() {
        assertFalse(Validatable("foo").hasLengthNotEqualTo(3).satisfied)
    }
    //endregion

    //region hasLengthLowerThan
    @Test
    fun __hasLengthLowerThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthLowerThan(0).satisfied)
    }

    @Test
    fun __hasLengthLowerThan__succeeds_when_the_length_is_lower_than_the_provided_one() {
        assertTrue(Validatable("foo").hasLengthLowerThan(4).satisfied)
    }

    @Test
    fun __hasLengthLowerThan__fails_when_the_length_is_greater_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable("foo").hasLengthLowerThan(2).satisfied, "The constraint is not satisfied when the length is greater than the provided one")
        assertFalse(Validatable("foo").hasLengthLowerThan(3).satisfied, "The constraint is not satisfied when the length is equal to the provided one")
    }
    //endregion

    //region hasLengthLowerThanOrEqualTo
    @Test
    fun __hasLengthLowerThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthLowerThanOrEqualTo(0).satisfied)
    }

    @Test
    fun __hasLengthLowerThanOrEqualTo__succeeds_when_the_length_is_lower_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable("foo").hasLengthLowerThanOrEqualTo(4).satisfied, "The constraint is satisfied when the length is lower than the provided one")
        assertTrue(Validatable("foo").hasLengthLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the length is equal to the provided one")
    }

    @Test
    fun __hasLengthLowerThanOrEqualTo__fails_when_the_length_is_greater_than_the_provided_one() {
        assertFalse(Validatable("foo").hasLengthLowerThanOrEqualTo(2).satisfied)
    }
    //endregion

    //region hasLengthGreaterThan
    @Test
    fun __hasLengthGreaterThan__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthGreaterThan(0).satisfied)
    }

    @Test
    fun __hasLengthGreaterThan__succeeds_when_the_length_is_greater_than_the_provided_one() {
        assertTrue(Validatable("foo").hasLengthGreaterThan(2).satisfied)
    }

    @Test
    fun __hasLengthGreaterThan__fails_when_the_length_is_lower_than_or_equal_to_the_provided_one() {
        assertFalse(Validatable("foo").hasLengthGreaterThan(4).satisfied, "The constraint is not satisfied when the length is lower than the provided one")
        assertFalse(Validatable("foo").hasLengthGreaterThan(3).satisfied, "The constraint is not satisfied when the length is equal to the provided one")
    }
    //endregion

    //region hasLengthGreaterThanOrEqualTo
    @Test
    fun __hasLengthGreaterThanOrEqualTo__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthGreaterThanOrEqualTo(0).satisfied)
    }

    @Test
    fun __hasLengthGreaterThanOrEqualTo__succeeds_when_the_length_is_greater_than_or_equal_to_the_provided_one() {
        assertTrue(Validatable("foo").hasLengthGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the length is greater than the provided one")
        assertTrue(Validatable("foo").hasLengthGreaterThanOrEqualTo(3).satisfied, "The constraint is satisfied when the length is equal to the provided one")
    }

    @Test
    fun __hasLengthGreaterThanOrEqualTo__fails_when_the_length_is_lower_than_the_provided_one() {
        assertFalse(Validatable("foo").hasLengthGreaterThanOrEqualTo(4).satisfied)
    }
    //endregion

    //region hasLengthBetween
    @Test
    fun __hasLengthBetween__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).hasLengthBetween(-1..0).satisfied)
    }

    @Test
    fun __hasLengthBetween__succeeds_when_the_length_is_within_the_provided_range() {
        assertTrue(Validatable("foo").hasLengthBetween(2..4).satisfied)
    }

    @Test
    fun __hasLengthBetween__fails_when_the_length_is_outside_the_provided_range() {
        assertFalse(Validatable("foo").hasLengthBetween(1..2).satisfied)
    }
    //endregion

    //region isMatching
    @Test
    fun __isMatching__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isMatching(Regex("")).satisfied)
    }

    @Test
    fun __isMatching__succeeds_when_the_value_matches_the_provided_regular_expression() {
        assertTrue(Validatable("foo").isMatching(Regex("^fo{2}$")).satisfied)
    }

    @Test
    fun __isMatching__fails_when_the_value_does_not_match_the_provided_regular_expression() {
        assertFalse(Validatable("foo").isMatching(Regex("^fo{1}$")).satisfied)
    }
    //endregion

    //region isNotMatching
    @Test
    fun __isNotMatching__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isNotMatching(Regex("")).satisfied)
    }

    @Test
    fun __isNotMatching__succeeds_when_the_value_matches_the_provided_regular_expression() {
        assertTrue(Validatable("foo").isNotMatching(Regex("^fo{1}$")).satisfied)
    }

    @Test
    fun __isNotMatching__fails_when_the_value_does_not_match_the_provided_regular_expression() {
        assertFalse(Validatable("foo").isNotMatching(Regex("^fo{2}")).satisfied)
    }
    //endregion

    //region isStartingWith
    @Test
    fun __isStartingWith__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isStartingWith("").satisfied)
    }

    @Test
    fun __isStartingWith__succeeds_when_the_value_starts_with_the_provided_char_sequence() {
        assertTrue(Validatable("foobar").isStartingWith("foo").satisfied)
    }

    @Test
    fun __isStartingWith__fails_when_the_value_does_not_start_with_the_provided_char_sequence() {
        assertFalse(Validatable("bar").isStartingWith("foo").satisfied)
    }
    //endregion

    //region isNotStartingWith
    @Test
    fun __isNotStartingWith__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isNotStartingWith("").satisfied)
    }

    @Test
    fun __isNotStartingWith__succeeds_when_the_value_does_not_start_with_the_provided_char_sequence() {
        assertTrue(Validatable("bar").isNotStartingWith("foo").satisfied)
    }

    @Test
    fun __isNotStartingWith__fails_when_the_value_starts_with_the_provided_char_sequence() {
        assertFalse(Validatable("foobar").isNotStartingWith("foo").satisfied)
    }
    //endregion

    //region isEndingWith
    @Test
    fun __isEndingWith__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isEndingWith("").satisfied)
    }

    @Test
    fun __isEndingWith__succeeds_when_the_value_ends_with_the_provided_char_sequence() {
        assertTrue(Validatable("foobar").isEndingWith("bar").satisfied)
    }

    @Test
    fun __isEndingWith__fails_when_the_value_does_not_end_with_the_provided_char_sequence() {
        assertFalse(Validatable("foo").isEndingWith("bar").satisfied)
    }
    //endregion

    //region isNotEndingWith
    @Test
    fun __isNotEndingWith__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isNotEndingWith("").satisfied)
    }

    @Test
    fun __isNotEndingWith__succeeds_when_the_value_does_not_end_with_the_provided_char_sequence() {
        assertTrue(Validatable("foo").isNotEndingWith("bar").satisfied)
    }

    @Test
    fun __isNotEndingWith__fails_when_the_value_ends_with_the_provided_char_sequence() {
        assertFalse(Validatable("foobar").isNotEndingWith("bar").satisfied)
    }
    //endregion

    //region isContaining
    @Test
    fun __isContaining__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isContaining("").satisfied)
    }

    @Test
    fun __isContaining__succeeds_when_the_value_contains_the_provided_char_sequence() {
        assertTrue(Validatable("foobarbaz").isContaining("bar").satisfied)
    }

    @Test
    fun __isContaining__fails_when_the_value_does_not_contain_the_provided_char_sequence() {
        assertFalse(Validatable("foobaz").isContaining("bar").satisfied)
    }
    //endregion

    //region isNotContaining
    @Test
    fun __isNotContaining__succeeds_when_the_value_is_null() {
        assertTrue(Validatable<CharSequence?>(null).isNotContaining("").satisfied)
    }

    @Test
    fun __isNotContaining__succeeds_when_the_value_does_not_contain_the_provided_char_sequence() {
        assertTrue(Validatable("foobaz").isNotContaining("bar").satisfied)
    }

    @Test
    fun __isNotContaining__fails_when_the_value_contains_the_provided_char_sequence() {
        assertFalse(Validatable("foobarbaz").isNotContaining("bar").satisfied)
    }
    //endregion
}
