package com.sycosoft.allsee.presentation.viewmodels

import app.cash.turbine.test
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.Balance
import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.BalanceType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import com.sycosoft.allsee.domain.usecases.GetAccountsUseCase
import com.sycosoft.allsee.domain.usecases.GetBalanceUseCase
import com.sycosoft.allsee.presentation.components.cards.balancecard.BalanceCardType
import com.sycosoft.allsee.presentation.components.text.DynamicTextType
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class HomePageViewModelTest {
// region Variables and Setup/Teardown

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var underTest: HomePageViewModel

    private val getAccountsUseCase: GetAccountsUseCase = mockk(relaxed = true)
    private val getBalanceUseCase: GetBalanceUseCase = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

// endregion
// region Placeholder View States

    @Test
    fun whenInitialisingViewStateEmitsPlaceholder_ThenViewStateEmitsLoadedValue() = runTest {
        val expectedInitialViewState = HomePageViewModel.ViewState(
            clearedBalance = BalanceCardType.Placeholder,
            accountName = DynamicTextType.Placeholder,
        )
        val expectedFinalViewState = HomePageViewModel.ViewState(
            accountName = DynamicTextType.Value("Test Account"),
            clearedBalance = BalanceCardType.Value("£10.00"),
        )

        coEvery { getAccountsUseCase() } returns listOf(
            Account(
                accountUid = UUID.randomUUID(),
                accountType = AccountType.PRIMARY,
                defaultCategory = UUID.randomUUID(),
                currency = CurrencyType.GBP,
                createdAt = OffsetDateTime.now(),
                name = "Test Account",
                accountIdentifier = "123456789",
                bankIdentifier = "123456",
                iban = "GB27L12345678123456",
                bic = "12345678",
            )
        )
        coEvery { getBalanceUseCase(any()) } returns Balance(
            currency = CurrencyType.GBP,
            type = BalanceType.CLEARED_BALANCE,
            minorUnits = 1000,
        )

        underTest = HomePageViewModel(getAccountsUseCase, getBalanceUseCase)

        underTest.viewState.test {
            assertEquals(expectedInitialViewState, awaitItem())
            assertEquals(expectedFinalViewState, awaitItem())

            expectNoEvents()
        }
    }

// endregion
}