package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CharSequenceTest {
    //region empty
    @Test
    fun `'empty' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).empty().satisfied)
    }

    @Test
    fun `'empty' succeeds when the value is empty`() {
        assertTrue(Validatable("").empty().satisfied)
    }

    @Test
    fun `'empty' fails when the value is not empty`() {
        assertFalse(Validatable("foo").empty().satisfied)
    }
    //endregion

    //region notEmpty
    @Test
    fun `'notEmpty' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).notEmpty().satisfied)
    }

    @Test
    fun `'notEmpty' succeeds when the value is not empty`() {
        assertTrue(Validatable("foo").notEmpty().satisfied)
    }

    @Test
    fun `'notEmpty' fails when the value is empty`() {
        assertFalse(Validatable("").notEmpty().satisfied)
    }
    //endregion

    //region lengthLowerThan
    @Test
    fun `'lengthLowerThan' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).lengthLowerThan(0).satisfied)
    }

    @Test
    fun `'lengthLowerThan' succeeds when the length is lower than the provided one`() {
        assertTrue(Validatable("foo").lengthLowerThan(4).satisfied)
    }

    @Test
    fun `'lengthLowerThan' fails when the length is greater than or equal to the provided one`() {
        assertFalse(Validatable("foo").lengthLowerThan(2).satisfied, "The constraint is not satisfied when the length is greater than the provided one")
        assertFalse(Validatable("foo").lengthLowerThan(3).satisfied, "The constraint is not satisfied when the length is equal to the provided one")
    }
    //endregion

    //region lengthLowerThanOrEqualTo
    @Test
    fun `'lengthLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).lengthLowerThanOrEqualTo(0).satisfied)
    }

    @Test
    fun `'lengthLowerThanOrEqualTo' succeeds when the length is lower than or equal to the provided one`() {
        assertTrue(Validatable("foo").lengthLowerThanOrEqualTo(4).satisfied, "The constraint is satisfied when the length is lower than the provided one")
        assertTrue(Validatable("foo").lengthLowerThanOrEqualTo(3).satisfied, "The constraint is satisfied when the length is equal to the provided one")
    }

    @Test
    fun `'lengthLowerThanOrEqualTo' fails when the length is greater than the provided one`() {
        assertFalse(Validatable("foo").lengthLowerThanOrEqualTo(2).satisfied)
    }
    //endregion

    //region lengthGreaterThan
    @Test
    fun `'lengthGreaterThan' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).lengthGreaterThan(0).satisfied)
    }

    @Test
    fun `'lengthGreaterThan' succeeds when the length is greater than the provided one`() {
        assertTrue(Validatable("foo").lengthGreaterThan(2).satisfied)
    }

    @Test
    fun `'lengthGreaterThan' fails when the length is lower than or equal to the provided one`() {
        assertFalse(Validatable("foo").lengthGreaterThan(4).satisfied, "The constraint is not satisfied when the length is lower than the provided one")
        assertFalse(Validatable("foo").lengthGreaterThan(3).satisfied, "The constraint is not satisfied when the length is equal to the provided one")
    }
    //endregion

    //region lengthGreaterThanOrEqualTo
    @Test
    fun `'lengthGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).lengthGreaterThanOrEqualTo(0).satisfied)
    }

    @Test
    fun `'lengthGreaterThanOrEqualTo' succeeds when the length is greater than or equal to the provided one`() {
        assertTrue(Validatable("foo").lengthGreaterThanOrEqualTo(2).satisfied, "The constraint is satisfied when the length is greater than the provided one")
        assertTrue(Validatable("foo").lengthGreaterThanOrEqualTo(3).satisfied, "The constraint is satisfied when the length is equal to the provided one")
    }

    @Test
    fun `'lengthGreaterThanOrEqualTo' fails when the length is lower than the provided one`() {
        assertFalse(Validatable("foo").lengthGreaterThanOrEqualTo(4).satisfied)
    }
    //endregion

    //region lengthBetween
    @Test
    fun `'lengthBetween' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).lengthBetween(-1..0).satisfied)
    }

    @Test
    fun `'lengthBetween' succeeds when the length is within the provided range`() {
        assertTrue(Validatable("foo").lengthBetween(2..4).satisfied)
    }

    @Test
    fun `'lengthBetween' fails when the length is outside the provided range`() {
        assertFalse(Validatable("foo").lengthBetween(1..2).satisfied)
    }
    //endregion

    //region startingWith
    @Test
    fun `'startingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).startingWith("").satisfied)
    }

    @Test
    fun `'startingWith' succeeds when the value starts with the provided char sequence`() {
        assertTrue(Validatable("foobar").startingWith("foo").satisfied)
    }

    @Test
    fun `'startingWith' fails when the value does not start with the provided char sequence`() {
        assertFalse(Validatable("bar").startingWith("foo").satisfied)
    }
    //endregion

    //region notStartingWith
    @Test
    fun `'notStartingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).notStartingWith("").satisfied)
    }

    @Test
    fun `'notStartingWith' succeeds when the value does not start with the provided char sequence`() {
        assertTrue(Validatable("bar").notStartingWith("foo").satisfied)
    }

    @Test
    fun `'notStartingWith' fails when the value starts with the provided char sequence`() {
        assertFalse(Validatable("foobar").notStartingWith("foo").satisfied)
    }
    //endregion

    //region endingWith
    @Test
    fun `'endingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).endingWith("").satisfied)
    }

    @Test
    fun `'endingWith' succeeds when the value ends with the provided char sequence`() {
        assertTrue(Validatable("foobar").endingWith("bar").satisfied)
    }

    @Test
    fun `'endingWith' fails when the value does not end with the provided char sequence`() {
        assertFalse(Validatable("foo").endingWith("bar").satisfied)
    }
    //endregion

    //region notEndingWith
    @Test
    fun `'notEndingWith' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).notEndingWith("").satisfied)
    }

    @Test
    fun `'notEndingWith' succeeds when the value does not end with the provided char sequence`() {
        assertTrue(Validatable("foo").notEndingWith("bar").satisfied)
    }

    @Test
    fun `'notEndingWith' fails when the value ends with the provided char sequence`() {
        assertFalse(Validatable("foobar").notEndingWith("bar").satisfied)
    }
    //endregion

    //region containing
    @Test
    fun `'containing' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).containing("").satisfied)
    }

    @Test
    fun `'containing' succeeds when the value contains the provided char sequence`() {
        assertTrue(Validatable("foobarbaz").containing("bar").satisfied)
    }

    @Test
    fun `'containing' fails when the value does not contain the provided char sequence`() {
        assertFalse(Validatable("foobaz").containing("bar").satisfied)
    }
    //endregion

    //region notContaining
    @Test
    fun `'notContaining' succeeds when the value is null`() {
        assertTrue(Validatable<CharSequence?>(null).notContaining("").satisfied)
    }

    @Test
    fun `'notContaining' succeeds when the value does not contain the provided char sequence`() {
        assertTrue(Validatable("foobaz").notContaining("bar").satisfied)
    }

    @Test
    fun `'notContaining' fails when the value contains the provided char sequence`() {
        assertFalse(Validatable("foobarbaz").notContaining("bar").satisfied)
    }
    //endregion
}
