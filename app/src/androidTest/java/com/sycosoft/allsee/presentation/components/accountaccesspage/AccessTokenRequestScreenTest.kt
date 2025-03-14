package com.sycosoft.allsee.presentation.components.accountaccesspage

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.sycosoft.allsee.presentation.components.screens.accountaccesspage.AccessTokenRequestScreen
import com.sycosoft.allsee.presentation.components.screens.accountaccesspage.AccessTokenRequestScreenTestTags
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

private typealias ATRSTT = AccessTokenRequestScreenTestTags

class AccessTokenRequestScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    /*
     * =============================================================================================
     * == Default State                                                                           ==
     * =============================================================================================
     */

    @Test
    fun whenScreenIsInDefaultState_thenEnsureCorrectComponentsAreDisplayed() {

        with (composeTestRule) {
            setContent {
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

            onNodeWithTag(ATRSTT.TITLE).assertIsDisplayed()
            onNodeWithTag(ATRSTT.TEXT).assertIsDisplayed()
            onNodeWithTag(ATRSTT.ACCESS_TOKEN_INPUT).assertIsDisplayed()
            onNodeWithTag(ATRSTT.BUTTON_GET_STARTED).assertIsDisplayed().assertIsNotEnabled()
            onNodeWithTag(ATRSTT.PROGRESS_BAR).isNotDisplayed()
        }
    }

    /*
     * =============================================================================================
     * == Access Token Input                                                                      ==
     * =============================================================================================
     */

    @Test
    fun whenProvidedWithString_thenAccessTokenOTFShouldDisplayThatString() {
        val expected = "test_token"

        with (composeTestRule) {
            // When
            setContent {
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
            onNodeWithTag(ATRSTT.ACCESS_TOKEN_INPUT).assertTextEquals(expected)
        }
    }

    @Test
    fun whenAccessTokenOTFIsChanged_thenAccessTokenShouldUpdate() {
        var accessToken = ""
        val expected = "test_token"

        with (composeTestRule) {
            setContent {
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

            onNodeWithTag(ATRSTT.ACCESS_TOKEN_INPUT).performTextInput(expected)

            assertEquals(expected, accessToken)
        }
    }

    /*
     * =============================================================================================
     * == Get Started Button                                                                      ==
     * =============================================================================================
     */

    @Test
    fun whenButtonIsPressed_thenProgressBarShouldBeDisplayed() {
        var showProgressBar = false

        with (composeTestRule) {
            // When
            setContent {
                AllSeeTheme {
                    Surface {
                        AccessTokenRequestScreen(
                            accessToken = "Test",
                            onAccessTokenChange = {},
                            onButtonClick = { showProgressBar = true },
                            showProgressBar = showProgressBar,
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(ATRSTT.BUTTON_GET_STARTED).assertIsEnabled().performClick()
            onNodeWithTag(ATRSTT.PROGRESS_BAR).isDisplayed()
        }
    }
}