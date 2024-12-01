package com.sycosoft.allsee.presentation.viewmodels

import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.domain.usecases.SaveTokenUseCase
import com.sycosoft.allsee.presentation.mappers.NameAndAccountTypeMapper
import com.sycosoft.allsee.presentation.utils.UiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

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

        assertEquals("new_token", underTest.accessToken.value)
    }

    @Test
    fun `When saveToken is called, Then loadingState is updated and person is loaded`() = runTest {
        val person = Person(
            uid = "123456789",
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

        testScope.backgroundScope.launch(UnconfinedTestDispatcher(testScope.testScheduler)) {
            underTest.loadingState.collect {
                collectedStates.add(it)
            }
        }

        underTest.updateAccessToken("test_token")

        underTest.saveToken()

        assertEquals(3, collectedStates.size)
        assertEquals(UiState.Initial, collectedStates[0])
        assertEquals(UiState.Loading, collectedStates[1])
        assertEquals(UiState.Success(NameAndAccountTypeMapper.map(person)), collectedStates[2])
    }

    @Test
    fun `When saveToken called, Given getPersonUseCase throws exception, Then loadingState is updated `() = runTest {
        // When
        val exception = RepositoryException(ErrorResponse("error", "Error Description"))
        val expected = UiState.Error(exception.error.error, exception.error.errorDescription)

        // Given
        coEvery { getPersonUseCase() } throws exception

        underTest.updateAccessToken("test_token")
        underTest.saveToken()
        val actual = underTest.loadingState.value

        // Verify
        assertEquals(expected, actual)
    }
}