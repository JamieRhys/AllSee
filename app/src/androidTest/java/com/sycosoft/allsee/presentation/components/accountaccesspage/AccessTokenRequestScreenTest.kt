package com.sycosoft.allsee.presentation.components.accountaccesspage

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sycosoft.allsee.presentation.components.screens.accountaccesspage.AccessTokenRequestScreen
import com.sycosoft.allsee.presentation.components.screens.accountaccesspage.AccessTokenRequestScreenTestTags
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
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.TITLE).isDisplayed()
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.TEXT).isDisplayed()
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.BUTTON_GET_STARTED).isDisplayed()
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.ACCESS_TOKEN_INPUT).isDisplayed()
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.PROGRESS_BAR).isNotDisplayed()
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
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.ACCESS_TOKEN_INPUT).assertTextEquals(expected)
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

        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.ACCESS_TOKEN_INPUT).performTextInput(expected)

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
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.BUTTON_GET_STARTED).performClick()
        composeTestRule.onNodeWithTag(AccessTokenRequestScreenTestTags.PROGRESS_BAR).isDisplayed()
    }
}