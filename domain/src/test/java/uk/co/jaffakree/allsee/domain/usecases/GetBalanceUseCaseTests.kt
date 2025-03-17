package uk.co.jaffakree.allsee.domain.usecases

import uk.co.jaffakree.allsee.domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.models.Balance
import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType

class GetBalanceUseCaseTests {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetBalanceUseCase

    @Before
    fun setup() {
        underTest = GetBalanceUseCase(repository)
    }

    @Test
    fun whenUseCaseIsCalled_ThenInvokeDelegatesToRepositoryAndReturnsBalance() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 500,
            type = BalanceType.AMOUNT
        )
        coEvery { repository.getBalance(any()) } returns expected

        val actual = underTest(BalanceType.AMOUNT)

        assertEquals(expected, actual)
        coVerify(exactly = 1) { repository.getBalance(any()) }
    }

    @Test(expected = RepositoryException::class)
    fun whenUseCaseIsCalled_GivenRepositoryExceptionIsThrown_ThenExceptionIsPropagatedToCaller() = runTest {
        val exception = RepositoryException(ErrorResponse(error = "error", errorDescription = ""))
        coEvery { repository.getBalance(any()) } throws exception

        underTest(BalanceType.AMOUNT)
    }
}