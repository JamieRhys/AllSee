package com.sycosoft.allsee.presentation.viewmodels

import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.domain.usecases.SaveTokenUseCase
import com.sycosoft.allsee.presentation.utils.UiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccountAccessPageViewModelTest {
    private val saveTokenUseCase = mockk<SaveTokenUseCase>(relaxed = true)
    private val getPersonUseCase = mockk<GetPersonUseCase>(relaxed = true)

    private val testScope = TestScope()

    private lateinit var underTest: AccountAccessPageViewModel


    @Before
    fun setUp() {
        underTest = AccountAccessPageViewModel(saveTokenUseCase, getPersonUseCase)
        Dispatchers.setMain(UnconfinedTestDispatcher(testScope.testScheduler))
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

    /** List comes out blank and might be due to it being initialised lazily. Need to ask if changing to eager will be an issue.
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
        coEvery { getPersonUseCase() } returns person
        val collectedStates = mutableListOf<UiState<NameAndAccountType>>()

        // Act
        underTest.updateAccessToken("test_token")
        val job = launch {
            underTest.viewState
                .map { it.nameAndAccountState }
                .toList(collectedStates)
        }
        underTest.saveToken()
        job.cancelAndJoin() // Ensure collection stops

        // Assert
        assertEquals(
            listOf(
                UiState.Initial,
                UiState.Loading,
                UiState.Success(NameAndAccountTypeMapper.map(person))
            ),
            collectedStates
        )
    }
    */

    @Test
    fun `When saveToken called, Given getPersonUseCase throws exception, Then loadingState is updated `() = runTest {
        // When
        val exception = RepositoryException(ErrorResponse("error", "Error Description"))
        val expected = UiState.Error(exception.error.error, exception.error.errorDescription)

        // Given
        coEvery { getPersonUseCase() } throws exception

        underTest.updateAccessToken("test_token")
        underTest.saveToken()
        val actual = underTest.viewState.value.nameAndAccountState

        // Verify
        assertEquals(expected, actual)
    }
}