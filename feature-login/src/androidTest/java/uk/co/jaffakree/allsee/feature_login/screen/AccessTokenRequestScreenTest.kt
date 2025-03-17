package uk.co.jaffakree.allsee.feature_login.screen

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
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme

class AccessTokenRequestScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

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

            with (AccessTokenRequestScreenTestTags) {
                onNodeWithTag(TITLE).assertIsDisplayed()
                onNodeWithTag(TEXT).assertIsDisplayed()
                onNodeWithTag(ACCESS_TOKEN_INPUT).assertIsDisplayed()
                onNodeWithTag(BUTTON_GET_STARTED).assertIsDisplayed().assertIsNotEnabled()
                onNodeWithTag(PROGRESS_BAR).isNotDisplayed()
            }
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

            with (AccessTokenRequestScreenTestTags) {
                // Then and Verify
                onNodeWithTag(ACCESS_TOKEN_INPUT).assertTextEquals(expected)
            }
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

            with (AccessTokenRequestScreenTestTags) {
                onNodeWithTag(ACCESS_TOKEN_INPUT).performTextInput(expected)

                Assert.assertEquals(expected, accessToken)
            }
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

            with (AccessTokenRequestScreenTestTags) {
                // Then and Verify
                onNodeWithTag(BUTTON_GET_STARTED).assertIsEnabled().performClick()
                onNodeWithTag(PROGRESS_BAR).isDisplayed()
            }
        }
    }
}