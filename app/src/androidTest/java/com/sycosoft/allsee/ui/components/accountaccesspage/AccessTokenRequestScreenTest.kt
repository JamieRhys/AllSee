package com.sycosoft.allsee.ui.components.accountaccesspage

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.requestFocus
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccessTokenRequestScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAccessTokenScreen_displaysCorrectly() {
        composeTestRule.setContent {
            // No need to assign anything in this section as we are not testing if they work.
            AccessTokenRequestScreen(
                accessToken = "",
                onAccessTokenChange = {},
                onGetStartedButtonClick = {}
            )
        }

        // Get all nodes with their test tags.
        val titleText = composeTestRule.onNodeWithTag("aap_title")
        val hintText = composeTestRule.onNodeWithTag("aap_text")
        val tokenInput = composeTestRule.onNodeWithTag("otf_access_token")
        val buttonGetStarted = composeTestRule.onNodeWithTag("button_get_started")

        // Check each component exists and is properly displayed.
        titleText.assertExists()
        titleText.assertIsDisplayed()

        hintText.assertExists()
        hintText.assertIsDisplayed()

        tokenInput.assertExists()
        tokenInput.assertIsDisplayed()

        buttonGetStarted.assertExists()
        buttonGetStarted.assertIsDisplayed()
    }

    @Test
    fun testAccessTokenInput_updatesAccessToken_whenTextInputted() = runTest {
        // Use mutableStateOf to hold access token
        val accessToken = mutableStateOf("")
        val testString = "test_access_token"

        composeTestRule.setContent {
            AccessTokenRequestScreen(
                accessToken = accessToken.value,
                onAccessTokenChange = { accessToken.value = it },
                onGetStartedButtonClick = {}
            )
        }

        val input = composeTestRule.onNodeWithTag("otf_access_token")

        // Ensure the input is focused
        input.requestFocus()
        input.performTextInput(testString)

        // Wait for any state changes to settle
        composeTestRule.awaitIdle()

        // Assert that the input field has the correct text
        input.assertTextEquals(testString)

        // Assert that the state variable also has the updated value
        assertEquals(testString, accessToken.value)
    }

    @Test
    fun testButtonGetStarted_isClickable() {
        var buttonClicked = false

        composeTestRule.setContent {
            AccessTokenRequestScreen(
                accessToken = "",
                onAccessTokenChange = {},
                onGetStartedButtonClick = { buttonClicked = true }
            )
        }

        val buttonGetStarted = composeTestRule.onNodeWithTag("button_get_started")

        buttonGetStarted.performClick()

        assertTrue(buttonClicked)
    }

}