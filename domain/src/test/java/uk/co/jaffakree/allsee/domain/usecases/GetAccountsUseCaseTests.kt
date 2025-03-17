package uk.co.jaffakree.allsee.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import uk.co.jaffakree.allsee.domain.models.types.AccountType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import uk.co.jaffakree.allsee.domain.repository.AppRepository
import java.time.OffsetDateTime
import java.util.UUID

class GetAccountsUseCaseTests {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetAccountsUseCase

    @Before
    fun setup() {
        underTest = GetAccountsUseCase(repository)
    }

    @Test
    fun whenUseCaseIsCalled_ThenInvokeDelegatesToRepositoryAndReturnsAccounts() = runTest {
        val expected = listOf(Account(
            accountUid = UUID.randomUUID(),
            accountType = AccountType.PRIMARY,
            defaultCategory = UUID.randomUUID(),
            currency = CurrencyType.GBP,
            createdAt = OffsetDateTime.now(),
            name = "Primary",
            accountIdentifier = "12345678",
            bankIdentifier = "123456",
            iban = "GB12345612345678",
            bic = "GB123456"
        ))
        coEvery { repository.getAccounts() } returns expected

        val actual = underTest()

        assertEquals(expected, actual)
        coVerify(exactly = 1) { repository.getAccounts() }
    }

    @Test(expected = RepositoryException::class)
    fun whenUseCaseIsCalled_GivenRepositoryExceptionIsThrown_ThenExceptionIsPropagatedToCaller() = runTest {
        val exception = RepositoryException(ErrorResponse(error = "error", errorDescription = ""))
        coEvery { repository.getAccounts() } throws exception

        underTest()
    }

    @Test
    fun whenUseCaseIsCalled_GivenEmptyList_ThenEmptyListIsReturned() = runTest {
        val expected = emptyList<Account>()
        coEvery { repository.getAccounts() } returns expected

        val actual = underTest()

        assertEquals(expected, actual)
    }
}