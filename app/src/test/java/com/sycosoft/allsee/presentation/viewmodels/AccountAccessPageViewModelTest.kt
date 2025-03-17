package com.sycosoft.allsee.presentation.viewmodels

import app.cash.turbine.test
import uk.co.jaffakree.allsee.feature_login.mappers.NameAndAccountTypeMapper
import uk.co.jaffakree.allsee.core.ui.utils.UiState
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
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import uk.co.jaffakree.allsee.domain.usecases.GetPersonUseCase
import uk.co.jaffakree.allsee.domain.usecases.SaveTokenUseCase
import uk.co.jaffakree.allsee.feature_login.viewmodel.AccountAccessPageViewModel
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class AccountAccessPageViewModelTest {
    private val saveTokenUseCase = mockk<SaveTokenUseCase>(relaxed = true)
    private val getPersonUseCase = mockk<GetPersonUseCase>(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var underTest: AccountAccessPageViewModel


    @Before
    fun setUp() {
        underTest = AccountAccessPageViewModel(saveTokenUseCase, getPersonUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When updateAccessToken is called, Then accessToken is updated`() {
        underTest.updateAccessToken("new_token")

        assertEquals("new_token", underTest.viewState.value.accessToken)
    }

    @Test
    fun `When saveToken is called, Then viewState transitions through Initial, Loading, and Success`() = runTest {
        // Arrange
        val person = Person(
            uid = UUID.randomUUID(),
            type = AccountHolderType.INDIVIDUAL,
            title = "Mr",
            firstName = "John",
            lastName = "Doe",
            dob = LocalDate.parse("1975-01-01"),
            email = "john.doe@example.com",
            phone = "0123456789"
        )
        val expected = UiState.Success(NameAndAccountTypeMapper.map(person))
        coEvery { getPersonUseCase() } returns person

        underTest.viewState.test {
            assertEquals(UiState.Initial, awaitItem().nameAndAccountState)

            underTest.saveToken()

            assertEquals(expected, awaitItem().nameAndAccountState)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `When saveToken called, Given getPersonUseCase throws exception, Then loadingState is updated `() = runTest {
        // When
        val exception = RepositoryException(ErrorResponse("error", "Error Description"))
        val expected = UiState.Error(exception.error.error, exception.error.errorDescription)

        // Given
        coEvery { getPersonUseCase() } throws exception

        underTest.viewState.test {
            underTest.updateAccessToken("test_token")

            assertEquals(UiState.Initial, awaitItem().nameAndAccountState)

            underTest.saveToken()

            // Verify
            assertEquals(expected, awaitItem().nameAndAccountState)
        }
    }
}