package com.sycosoft.allsee.presentation.components.accountaccesspage

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class AccessTokenRequestScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsInDefaultState_thenEnsureCorrectComponentsAreDisplayed() {
        // When
        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    AccessTokenRequestScreen(
                        accessToken = "",
                        onAccessTokenChange = {},
                        onButtonClick = {},
                        showProgressBar = false,
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("atrs_title").isDisplayed()
        composeTestRule.onNodeWithTag("atrs_text").isDisplayed()
        composeTestRule.onNodeWithTag("atrs_button").isDisplayed()
        composeTestRule.onNodeWithTag("otf_access_token").isDisplayed()
        composeTestRule.onNodeWithTag("atrs_progressbar").isNotDisplayed()
    }

    @Test
    fun whenProvidedWithString_thenAccessTokenOTFShouldDisplayThatString() {
        val expected = "test_token"

        // When
        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    AccessTokenRequestScreen(
                        accessToken = expected,
                        onAccessTokenChange = {},
                        onButtonClick = {},
                        showProgressBar = false,
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("otf_access_token").assertTextEquals(expected)
    }

    @Test
    fun whenAccessTokenOTFIsChanged_thenAccessTokenShouldUpdate() {
        var accessToken = ""
        val expected = "test_token"

        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    AccessTokenRequestScreen(
                        accessToken = accessToken,
                        onAccessTokenChange = { accessToken = it },
                        onButtonClick = {},
                        showProgressBar = false,
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("otf_access_token").performTextInput(expected)

        assertEquals(expected, accessToken)
    }

    @Test
    fun whenButtonIsPressed_thenProgressBarShouldBeDisplayed() {
        var showProgressBar = false

        // When
        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    AccessTokenRequestScreen(
                        accessToken = "",
                        onAccessTokenChange = {},
                        onButtonClick = { showProgressBar = true },
                        showProgressBar = showProgressBar,
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("atrs_button").performClick()
        composeTestRule.onNodeWithTag("atrs_progressbar").isDisplayed()
    }
}