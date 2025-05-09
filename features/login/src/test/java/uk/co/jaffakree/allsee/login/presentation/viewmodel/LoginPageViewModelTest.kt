package uk.co.jaffakree.allsee.login.presentation.viewmodel

import app.cash.turbine.test
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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginPageViewModelTest {
// region Setup and Teardown

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var underTest: LoginPageViewModel

    @Before
    fun setup() {
        underTest = LoginPageViewModel()

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

// endregion
// region Tests

    @Test
    fun whenUpdateAccessTokenIsCalled_thenAccessTokenStringUpdated() = runTest {
        val expected = "Test Access Token"

        underTest.viewState.test {
            assertEquals("", awaitItem().accessToken)

            underTest.updateAccessToken(expected)

            assertEquals(expected, awaitItem().accessToken)
        }
    }

// endregion
}