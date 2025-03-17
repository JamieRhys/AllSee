package uk.co.jaffakree.allsee.feature_login.components.dialogs

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme

class UserConfirmationDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val expectedTitle = "Hold on a second!"
    private val expectedName = "Joe Bloggs"
    private val expectedAccountType = "Individual"
    private val expectedText = "Just to confirm, is your name $expectedName and you hold a $expectedAccountType account?"
    private val expectedConfirmButtonText = "Yes"
    private val expectedDismissButtonText = "No"

    @Test
    fun `User Confirmation Dialog renders correctly when provided correct values`() {
        // Act
        composeTestRule.setContent {
            AllSeeTheme {
                UserConfirmationDialog(
                    name = expectedName,
                    accountType = expectedAccountType,
                    onDismissButtonClick = {},
                    onConfirmButtonClick = {},
                    onDismissRequest = {},
                )
            }
        }

        with (UserConfirmationDialogTestTags) {
            // Verify
            composeTestRule.onNodeWithTag(TITLE).assertTextEquals(expectedTitle)
            composeTestRule.onNodeWithTag(TEXT).assertTextEquals(expectedText)
            composeTestRule.onNodeWithTag(CONFIRM_BUTTON).assertTextEquals(expectedConfirmButtonText)
            composeTestRule.onNodeWithTag(DISMISS_BUTTON).assertTextEquals(expectedDismissButtonText)
        }
    }

    @Test
    fun `User Confirmation Dialog Yes Button calls onConfirmButtonClick when fired`() {
        var buttonClicked = false
        // Act
        composeTestRule.setContent {
            AllSeeTheme {
                UserConfirmationDialog(
                    name = expectedName,
                    accountType = expectedAccountType,
                    onDismissRequest = {},
                    onConfirmButtonClick = { buttonClicked = true },
                    onDismissButtonClick = {},
                )
            }
        }

        with (UserConfirmationDialogTestTags) {
            // Verify
            composeTestRule.onNodeWithTag(CONFIRM_BUTTON).performClick()
            TestCase.assertTrue(buttonClicked)
        }
    }

    @Test
    fun `User Confirmation Dialog No Button calls onDismissButtonClick when fired`() {
        var buttonClicked = false
        // Act
        composeTestRule.setContent {
            AllSeeTheme {
                UserConfirmationDialog(
                    name = expectedName,
                    accountType = expectedAccountType,
                    onDismissRequest = {},
                    onConfirmButtonClick = {},
                    onDismissButtonClick = { buttonClicked = true },
                )
            }
        }

        with (UserConfirmationDialogTestTags) {
            // Verify
            composeTestRule.onNodeWithTag(DISMISS_BUTTON).performClick()
            TestCase.assertTrue(buttonClicked)
        }
    }
}