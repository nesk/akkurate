package dev.nesk.akkurate.constraints.builders

import dev.nesk.akkurate.validatables.Validatable
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * !!! THE FOLLOWING CODE MUST BE SYNCED ACROSS `ArrayTest`, `CollectionTest` AND `MapTest` !!!
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * The validation API is the same across `Array`, `Collection` and `Map` types, and so are the tests.
 * But, due to missing union types in Kotlin, we must duplicate the code for each of those tests.
 */
class CollectionTest {
    companion object {
        private val VALUE = listOf('f', 'o', 'o')
        private val EXACT_SIZE = VALUE.size
        private val SIZE_PLUS_ONE = VALUE.size + 1
        private val SIZE_MINUS_ONE = VALUE.size - 1
        private val VALIDATABLE = Validatable(VALUE)

        private const val NULL_SIZE = Int.MAX_VALUE // no matter the size
        private val NULL_VALIDATABLE = Validatable<List<*>?>(null)
    }

    //region sizeEqualTo

    @Test
    fun `'sizeEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.sizeEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'sizeEqualTo' succeeds when the size is equal to the provided one`() {
        assertTrue(VALIDATABLE.sizeEqualTo(EXACT_SIZE).satisfied)
    }

    @Test
    fun `'sizeEqualTo' fails when the size is different than the provided one`() {
        assertFalse(VALIDATABLE.sizeEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is not satisfied when the size is greater than the provided one")
        assertFalse(VALIDATABLE.sizeEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is not satisfied when the size is lower than the provided one")
    }

    //endregion

    //region sizeLowerThan

    @Test
    fun `'sizeLowerThan' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.sizeLowerThan(NULL_SIZE).satisfied)
    }

    @Test
    fun `'sizeLowerThan' succeeds when the size is lower than the provided one`() {
        assertTrue(VALIDATABLE.sizeLowerThan(SIZE_PLUS_ONE).satisfied)
    }

    @Test
    fun `'sizeLowerThan' fails when the size is greater than or equal to the provided one`() {
        assertFalse(VALIDATABLE.sizeLowerThan(SIZE_MINUS_ONE).satisfied, "The constraint is not satisfied when the size is greater than the provided one")
        assertFalse(VALIDATABLE.sizeLowerThan(EXACT_SIZE).satisfied, "The constraint is not satisfied when the size is equal to the provided one")
    }

    //endregion

    //region sizeLowerThanOrEqualTo

    @Test
    fun `'sizeLowerThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.sizeLowerThanOrEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'sizeLowerThanOrEqualTo' succeeds when the size is lower than or equal to the provided one`() {
        assertTrue(VALIDATABLE.sizeLowerThanOrEqualTo(SIZE_PLUS_ONE).satisfied, "The constraint is satisfied when the size is lower than the provided one")
        assertTrue(VALIDATABLE.sizeLowerThanOrEqualTo(EXACT_SIZE).satisfied, "The constraint is satisfied when the size is equal to the provided one")
    }

    @Test
    fun `'sizeLowerThanOrEqualTo' fails when the size is greater than the provided one`() {
        assertFalse(VALIDATABLE.sizeLowerThanOrEqualTo(SIZE_MINUS_ONE).satisfied)
    }

    //endregion

    //region sizeGreaterThan

    @Test
    fun `'sizeGreaterThan' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.sizeGreaterThan(NULL_SIZE).satisfied)
    }

    @Test
    fun `'sizeGreaterThan' succeeds when the size is greater than the provided one`() {
        assertTrue(VALIDATABLE.sizeGreaterThan(SIZE_MINUS_ONE).satisfied)
    }

    @Test
    fun `'sizeGreaterThan' fails when the size is lower than or equal to the provided one`() {
        assertFalse(VALIDATABLE.sizeGreaterThan(SIZE_PLUS_ONE).satisfied, "The constraint is not satisfied when the size is lower than the provided one")
        assertFalse(VALIDATABLE.sizeGreaterThan(EXACT_SIZE).satisfied, "The constraint is not satisfied when the size is equal to the provided one")
    }

    //endregion

    //region sizeGreaterThanOrEqualTo

    @Test
    fun `'sizeGreaterThanOrEqualTo' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.sizeGreaterThanOrEqualTo(NULL_SIZE).satisfied)
    }

    @Test
    fun `'sizeGreaterThanOrEqualTo' succeeds when the size is greater than or equal to the provided one`() {
        assertTrue(VALIDATABLE.sizeGreaterThanOrEqualTo(SIZE_MINUS_ONE).satisfied, "The constraint is satisfied when the size is greater than the provided one")
        assertTrue(VALIDATABLE.sizeGreaterThanOrEqualTo(EXACT_SIZE).satisfied, "The constraint is satisfied when the size is equal to the provided one")
    }

    @Test
    fun `'sizeGreaterThanOrEqualTo' fails when the size is lower than the provided one`() {
        assertFalse(VALIDATABLE.sizeGreaterThanOrEqualTo(SIZE_PLUS_ONE).satisfied)
    }

    //endregion

    //region sizeBetween

    @Test
    fun `'sizeBetween' succeeds when the value is null`() {
        assertTrue(NULL_VALIDATABLE.sizeBetween(NULL_SIZE..NULL_SIZE).satisfied)
    }

    @Test
    fun `'sizeBetween' succeeds when the size is between the provided range`() {
        assertTrue(VALIDATABLE.sizeBetween(SIZE_MINUS_ONE..SIZE_PLUS_ONE).satisfied)
    }

    @Test
    fun `'sizeBetween' fails when the size is outside the provided range`() {
        @Suppress("EmptyRange")
        assertFalse(VALIDATABLE.sizeBetween(1..0).satisfied)
    }

    //endregion
}
