package com.sycosoft.allsee.presentation.components.homepage

import androidx.compose.material3.Surface
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sycosoft.allsee.presentation.components.cards.balancecard.BalanceCardType
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreen
import com.sycosoft.allsee.presentation.components.screens.homepage.HomePageScreenTestTags
import uk.co.jaffakree.allsee.core.ui.components.text.DynamicTextType
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class HomePageScreenTest {
    @get:Rule val composeTestRule = createComposeRule()

// region Default State

    @Test
    fun whenScreenInNormalMode_thenEnsureCorrectComponentsAreDisplayed() {
        // When
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    Surface {
                        HomePageScreen(
                            accountName = DynamicTextType.Value("Individual"),
                            onPersonButtonClick = {},
                            clearedBalance = BalanceCardType.Value("£1,000"),
                            onBalanceCardClick = {},
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(HomePageScreenTestTags.TEXT_ACCOUNT_NAME, useUnmergedTree = true).isDisplayed()
            onNodeWithTag(HomePageScreenTestTags.BUTTON_PERSON).isDisplayed()
            onNodeWithTag(HomePageScreenTestTags.BALANCE_CARD, useUnmergedTree = true).isDisplayed()
        }

    }

    @Test
    fun whenScreenInPlaceholderMode_ThenEnsureCorrectComponentsAreDisplayed() {
        // When
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    HomePageScreen(
                        accountName = DynamicTextType.Placeholder,
                        onPersonButtonClick = {},
                        clearedBalance = BalanceCardType.Placeholder,
                        onBalanceCardClick = {},
                    )
                }
            }

            // Then and Verify
            onNodeWithTag("placeholder_${HomePageScreenTestTags.TEXT_ACCOUNT_NAME}").isDisplayed()
            onNodeWithTag(HomePageScreenTestTags.BUTTON_PERSON).isDisplayed()
            onNodeWithTag("placeholder_${HomePageScreenTestTags.BALANCE_CARD}").isDisplayed()
        }
    }

// endregion
// region Person Button

    @Test
    fun whenPersonButtonPressed_thenPersonButtonClickShouldFire() {
        // When
        var buttonClicked = false

        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    Surface {
                        HomePageScreen(
                            accountName = DynamicTextType.Placeholder,
                            onPersonButtonClick = { buttonClicked = true },
                            clearedBalance = BalanceCardType.Placeholder,
                            onBalanceCardClick = {},
                        )
                    }
                }
            }

            // Then and Verify
            onNodeWithTag(HomePageScreenTestTags.BUTTON_PERSON).performClick()
            assertTrue(buttonClicked)
        }
    }

// endregion
}