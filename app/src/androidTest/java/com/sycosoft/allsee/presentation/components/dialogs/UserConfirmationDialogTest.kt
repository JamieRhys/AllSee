package com.sycosoft.allsee.presentation.components.dialogs

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class UserConfirmationDialogTest {
    @get:Rule val composeTestRule = createComposeRule()

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

        // Verify
        composeTestRule.onNodeWithTag(UserConfirmationDialogTestTags.TITLE).assertTextEquals(expectedTitle)
        composeTestRule.onNodeWithTag(UserConfirmationDialogTestTags.TEXT).assertTextEquals(expectedText)
        composeTestRule.onNodeWithTag(UserConfirmationDialogTestTags.CONFIRM_BUTTON).assertTextEquals(expectedConfirmButtonText)
        composeTestRule.onNodeWithTag(UserConfirmationDialogTestTags.DISMISS_BUTTON).assertTextEquals(expectedDismissButtonText)
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

        // Verify
        composeTestRule.onNodeWithTag(UserConfirmationDialogTestTags.CONFIRM_BUTTON).performClick()
        assertTrue(buttonClicked)
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

        // Verify
        composeTestRule.onNodeWithTag(UserConfirmationDialogTestTags.DISMISS_BUTTON).performClick()
        assertTrue(buttonClicked)
    }
}