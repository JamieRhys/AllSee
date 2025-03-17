package uk.co.jaffakree.allsee.feature_home.screen

import androidx.compose.material3.Surface
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.core.ui.components.text.DynamicTextType
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import uk.co.jaffakree.allsee.feature_home.components.balancecard.BalanceCardType

class HomePageScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

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

            with (HomePageScreenTestTags) {
                // Then and Verify
                onNodeWithTag(TEXT_ACCOUNT_NAME, useUnmergedTree = true).isDisplayed()
                onNodeWithTag(BUTTON_PERSON).isDisplayed()
                onNodeWithTag(BALANCE_CARD, useUnmergedTree = true).isDisplayed()
            }
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

            with (HomePageScreenTestTags) {
                // Then and Verify
                onNodeWithTag("placeholder_${TEXT_ACCOUNT_NAME}").isDisplayed()
                onNodeWithTag(BUTTON_PERSON).isDisplayed()
                onNodeWithTag("placeholder_${BALANCE_CARD}").isDisplayed()
            }
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

            with (HomePageScreenTestTags) {
                // Then and Verify
                onNodeWithTag(BUTTON_PERSON).performClick()
                TestCase.assertTrue(buttonClicked)
            }
        }
    }

// endregion
}