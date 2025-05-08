package uk.co.jaffakree.allsee.login.presentation.components.dialogs

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.login.R

class UserConfirmationDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = ApplicationProvider.getApplicationContext<Context>()

// region Dialog Displayed
    @Test
    fun whenDisplayed_thenDialogIsShown() {
        with (composeTestRule) {
            setContent {
                UserConfirmationDialog(
                    name = "Jamie Edwards",
                    accountType = "Individual",
                    onDismissButtonClick = {},
                    onConfirmButtonClick = {},
                    onDismissRequest = {},
                )
            }

            with (UserConfirmationDialogTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(TEXT_TITLE)
                    .assertIsDisplayed()
                    .assertTextEquals(context.getString(R.string.user_confirmation_dialog_title))

                onNodeWithTag(TEXT_BODY)
                    .assertIsDisplayed()
                    .assertTextEquals(
                        context.getString(
                            R.string.user_confirmation_dialog_text,
                            "Jamie Edwards",
                            "Individual"
                        )
                    )

                onNodeWithTag(BUTTON_CONFIRM)
                    .assertIsDisplayed()
                    .assertIsEnabled()

                onNodeWithTag(BUTTON_CONFIRM_TEXT, useUnmergedTree = true)
                    .assertIsDisplayed()
                    .assertTextEquals(context.getString(uk.co.jaffakree.allsee.core.R.string.button_yes))

                onNodeWithTag(BUTTON_DISMISS)
                    .assertIsDisplayed()
                    .assertIsEnabled()

                onNodeWithTag(BUTTON_DISMISS_TEXT, useUnmergedTree = true)
                    .assertIsDisplayed()
                    .assertTextEquals(context.getString(uk.co.jaffakree.allsee.core.R.string.button_no))
            }
        }
    }

// endregion
// region Button Tests

    @Test
    fun whenConfirmButtonClicked_thenOnConfirmButtonClickIsCalled() {
        var clicked = false

        with (composeTestRule) {
            setContent {
                UserConfirmationDialog(
                    name = "",
                    accountType = "",
                    onDismissRequest = {},
                    onDismissButtonClick = {},
                    onConfirmButtonClick = { clicked = true },
                )
            }

            with (UserConfirmationDialogTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(BUTTON_CONFIRM)
                    .assertIsDisplayed()
                    .assertIsEnabled()
                    .performClick()

                assertTrue(clicked)
            }
        }
    }

    @Test
    fun whenDismissButtonClicked_thenOnDismissButtonClickIsCalled() {
        var clicked = false

        with (composeTestRule) {
            setContent {
                UserConfirmationDialog(
                    name = "",
                    accountType = "",
                    onDismissRequest = {},
                    onConfirmButtonClick = {},
                    onDismissButtonClick = { clicked = true }
                )
            }

            with (UserConfirmationDialogTestTags) {
                onNodeWithTag(COMPONENT).assertIsDisplayed()

                onNodeWithTag(BUTTON_DISMISS)
                    .assertIsDisplayed()
                    .assertIsEnabled()
                    .performClick()

                assertTrue(clicked)
            }
        }
    }

// endregion
}