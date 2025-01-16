package com.sycosoft.allsee.presentation.components.homepage

import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreen
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreenTestTags
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class HomePageScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun whenScreenInDefaultState_thenEnsureCorrectComponentsAreDisplayed() {
        // When
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    Surface {
                        HomePageScreen(
                            accountName = "Individual",
                            onPersonButtonClick = { /*TODO*/ },
                            clearedBalance = "£0.00",
                            onBalanceCardClick = { /* TODO */ },
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(HomePageScreenTestTags.TEXT_ACCOUNT_NAME).isDisplayed()
            onNodeWithTag(HomePageScreenTestTags.BUTTON_PERSON).isDisplayed()
            onNodeWithTag(HomePageScreenTestTags.BALANCE_CARD).isDisplayed()
        }

    }

    // #############################################################################################
    // ## Account Name Label                                                                      ##
    // #############################################################################################

    @Test
    fun whenScreenInitiallyShown_thenAccountNameLabelShouldDisplayLoadingText() {
        // When
        val expected = "Loading"
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    Surface {
                        HomePageScreen(
                            accountName = null,
                            onPersonButtonClick = {},
                            clearedBalance = "£0.00",
                            onBalanceCardClick = { /* TODO */ },
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(HomePageScreenTestTags.TEXT_ACCOUNT_NAME).assertTextEquals(expected)
        }
    }

    @Test
    fun whenScreenLoadedWithAccountName_thenAccountNameLabelShouldDisplayValue() {
        // When
        val expected = "Individual"
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    Surface {
                        HomePageScreen(
                            accountName = expected,
                            onPersonButtonClick = {},
                            clearedBalance = "£0.00",
                            onBalanceCardClick = { /* TODO */ },
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(HomePageScreenTestTags.TEXT_ACCOUNT_NAME).assertTextEquals(expected)
        }
    }

    // #############################################################################################
    // ## Person Button                                                                           ##
    // #############################################################################################

    @Test
    fun whenPersonButtonPressed_thenPersonButtonClickShouldFire() {
        // When
        var buttonClicked = false

        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    Surface {
                        HomePageScreen(
                            accountName = null,
                            onPersonButtonClick = { buttonClicked = true },
                            clearedBalance = "£0.00",
                            onBalanceCardClick = { /* TODO */ },
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(HomePageScreenTestTags.BUTTON_PERSON).performClick()
            assertTrue(buttonClicked)
        }
    }
}