package uk.co.jaffakree.allsee.login.presentation.screen

import android.content.Context
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.login.R

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

// region Normal Mode

    @Test
    fun whenNormallyDisplayed_thenQuestionScreenShown() {
        with (composeTestRule) {
            setContent {
                LoginScreen(
                    accessToken = "",
                    onAccessTokenChange = {},
                    showProgressBar = false,
                    onLoginButtonClick = {},
                )
            }

            with (LoginScreenTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(TEXT_TITLE)
                    .assertIsDisplayed()
                    .assertTextEquals(context.getString(R.string.title))

                onNodeWithTag(TEXT_GET_STARTED)
                    .assertIsDisplayed()
                    .assertTextEquals(context.getString(R.string.get_started))

                onNodeWithTag(TEXT_FIELD_ACCESS_TOKEN)
                    .assertIsDisplayed()
                    .assertTextContains(context.getString(R.string.access_token))
            }
        }
    }

// endregion
// region Access Token Text Field

    @Test
    fun whenProvidedWithString_thenAccessTokenInputShouldUpdate() {
        val expected = "Test Access Token"
        with (composeTestRule) {
            setContent {
                LoginScreen(
                    accessToken = expected,
                    onAccessTokenChange = {},
                    showProgressBar = false,
                    onLoginButtonClick = {}
                )
            }

            with (LoginScreenTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(TEXT_FIELD_ACCESS_TOKEN)
                    .assertIsDisplayed()
                    .assertTextContains(expected)
            }
        }
    }

    @Test
    fun whenAccessTokenIsChanged_thenInputShouldUpdate() {
        val expected = "Test Access Token"
        var actual = ""

        with (composeTestRule) {
            setContent {
                LoginScreen(
                    accessToken = actual,
                    onAccessTokenChange = { actual = it },
                    showProgressBar = false,
                    onLoginButtonClick = {}
                )
            }

            with (LoginScreenTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(TEXT_FIELD_ACCESS_TOKEN)
                    .assertIsDisplayed()
                    .performTextInput(expected)

                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun whenKeyboardEnterIsPressedOnAccessTokenInput_thenDoneImeActionShouldBePresent() {
        with (composeTestRule) {
            setContent {
                LoginScreen(
                    accessToken = "",
                    onAccessTokenChange = {},
                    showProgressBar = false,
                    onLoginButtonClick = {}
                )
            }

            with (LoginScreenTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(TEXT_FIELD_ACCESS_TOKEN)
                    .assert(hasImeAction(ImeAction.Done))
            }
        }
    }

// endregion
// region Button Tests

    @Test
    fun whenLoginButtonClicked_thenOnLoginButtonClickIsCalled() {
        var clicked = false

        with (composeTestRule) {
            setContent {
                LoginScreen(
                    accessToken = "",
                    onAccessTokenChange = {},
                    showProgressBar = false,
                    onLoginButtonClick = { clicked = true }
                )
            }

            with (LoginScreenTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(BUTTON_LOGIN)
                    .performClick()

                assertTrue(clicked)
            }
        }
    }

// endregion
// region Logging In Mode

    @Test
    fun whenShowProgressBarIsSet_thenLoggingInScreenShown() {
        with (composeTestRule) {
            setContent {
                LoginScreen(
                    accessToken = "",
                    onAccessTokenChange = {},
                    showProgressBar = true,
                    onLoginButtonClick = {}
                )
            }

            with (LoginScreenTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(PROGRESS_BAR).assertIsDisplayed()

                onNodeWithTag(TEXT_LOGGING_IN)
                    .assertIsDisplayed()
                    .assertTextEquals(context.getString(R.string.logging_in))
            }
        }
    }

// endregion
}