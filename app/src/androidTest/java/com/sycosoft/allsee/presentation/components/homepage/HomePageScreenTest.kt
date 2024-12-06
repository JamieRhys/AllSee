package com.sycosoft.allsee.presentation.components.homepage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class HomePageScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun whenScreenInDefaultState_thenEnsureCorrectComponentsAreDisplayed() {
        // When
        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    HomePageScreen(
                        accountName = "Individual",
                        onPersonButtonClick = { /*TODO*/ },
                        innerPadding = PaddingValues()
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("hps_account_name").isDisplayed()
        composeTestRule.onNodeWithTag("hps_person_button").isDisplayed()
    }

    // #############################################################################################
    // ## Account Name Label                                                                      ##
    // #############################################################################################

    @Test
    fun whenScreenInitiallyShown_thenAccountNameLabelShouldDisplayLoadingText() {
        // When
        val expected = "Loading"
        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    HomePageScreen(
                        accountName = null,
                        onPersonButtonClick = {},
                        innerPadding = PaddingValues()
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("hps_account_name").assertTextEquals(expected)
    }

    @Test
    fun whenScreenLoadedWithAccountName_thenAccountNameLabelShouldDisplayValue() {
        // When
        val expected = "Individual"
        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    HomePageScreen(
                        accountName = expected,
                        onPersonButtonClick = {},
                        innerPadding = PaddingValues()
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("hps_account_name").assertTextEquals(expected)
    }

    // #############################################################################################
    // ## Person Button                                                                           ##
    // #############################################################################################

    @Test
    fun whenPersonButtonPressed_thenPersonButtonClickShouldFire() {
        // When
        var buttonClicked = false

        composeTestRule.setContent {
            AllSeeTheme {
                Surface {
                    HomePageScreen(
                        accountName = null,
                        onPersonButtonClick = { buttonClicked = true },
                        innerPadding = PaddingValues()
                    )
                }
            }
        }

        // Then and Verify
        composeTestRule.onNodeWithTag("hps_person_button").performClick()
        assertTrue(buttonClicked)
    }
}