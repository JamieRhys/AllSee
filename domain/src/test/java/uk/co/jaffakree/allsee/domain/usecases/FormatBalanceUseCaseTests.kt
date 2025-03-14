package uk.co.jaffakree.allsee.domain.usecases

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class FormatBalanceUseCaseTests {
    private lateinit var underTest: FormatBalanceUseCase

    @Before
    fun setup() {
        underTest = FormatBalanceUseCase()
    }

    /*
     * =============================================================================================
     * == Positive Numbers                                                                        ==
     * =============================================================================================
     */

    @Test
    fun whenGivenPositiveNumber_ThenItShouldBeFormattedCorrectly() {
        val expected = "£753.32"
        val actual = underTest(75332)

        assertEquals(expected, actual)
    }

    @Test
    fun whenNumberWithLessThan10AfterDecimalIsProvided_ThenNumberShouldBeFormattedCorrectly() {
        val expected = "£500.01"
        val actual = underTest(50001)

        assertEquals(expected, actual)
    }

    /*
     * =============================================================================================
     * == Zero Numbers                                                                            ==
     * =============================================================================================
     */

    @Test
    fun whenNumberIsZero_ThenItShouldBeFormattedCorrectly() {
        val expected = "£0.00"
        val actual = underTest(0)

        assertEquals(expected, actual)
    }

    /*
     * =============================================================================================
     * == Negative Numbers                                                                        ==
     * =============================================================================================
     */

    @Test
    fun whenNumberIsNegative_ThenItShouldBeFormattedCorrectly() {
        val expected = "-£753.32"
        val actual = underTest(-75332)

        assertEquals(expected, actual)
    }

    @Test
    fun whenNegativeNumberHasDecimalLessThan10_ThenNumberShouldBeFormattedCorrectly() {
        val expected = "-£500.01"
        val actual = underTest(-50001)

        assertEquals(expected, actual)
    }
}