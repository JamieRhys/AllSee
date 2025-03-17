package uk.co.jaffakree.allsee.feature_home.components.cards

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import uk.co.jaffakree.allsee.feature_home.components.balancecard.BalanceCard
import uk.co.jaffakree.allsee.feature_home.components.balancecard.BalanceCardType
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.feature_home.components.balancecard.BalanceCardTestTags

class BalanceCardTests {
    @get:Rule val composeTestRule = createComposeRule()

// region Default State

    @Test
    fun balanceCardRendersCorrectlyWhenProvidedCorrectValues() {
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Value(clearedBalance = "£100.00"),
                        onClick = {}
                    )
                }
            }

            with (BalanceCardTestTags) {
                onNodeWithTag(BALANCE).isDisplayed()
                onNodeWithTag(ADD_FUNDS_ICON).isDisplayed()
            }
        }
    }

    @Test
    fun balanceCardPlaceholderRendersCorrectly() {
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Placeholder,
                        onClick = {}
                    )
                }
            }
        }
    }

// endregion
// region Balance

    @Test
    fun whenBalanceCardIsProvidedPositiveBalanceString_ThenEnsureItIsDisplayedCorrectly() {
        val expected = "£100.00"
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Value(clearedBalance = expected),
                        onClick = {},
                    )
                }
            }

            with (BalanceCardTestTags) {
                onNodeWithTag(BALANCE, useUnmergedTree = true).assertTextEquals(expected)
            }
        }
    }

    @Test
    fun whenBalanceCardIsProvidedWithZeroBalanceString_ThenEnsureItIsDisplayedCorrectly() {
        val expected = "£0.00"
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Value(clearedBalance = expected),
                        onClick = {},
                    )
                }
            }

            with (BalanceCardTestTags) {
                onNodeWithTag(BALANCE, useUnmergedTree = true).assertTextEquals(expected)
            }
        }
    }

    @Test
    fun whenBalanceCardIsProvidedWithNegativeBalanceString_ThenEnsureItIsDisplayedCorrectly() {
        val expected = "-£100.00"
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Value(clearedBalance = expected),
                        onClick = {},
                    )
                }
            }

            with (BalanceCardTestTags) {
                onNodeWithTag(BALANCE, useUnmergedTree = true).assertTextEquals(expected)
            }
        }
    }

// endregion
// region OnClick Trigger

    @Test
    fun whenBalanceCardIsClicked_ThenOnClickIsTriggered() {
        var clicked = false
        val balance = "£100.00"

        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Value(clearedBalance = balance),
                        onClick = { clicked = true },
                    )
                }
            }

            onNodeWithText(balance).performClick()
            assertTrue(clicked)
        }
    }

    @Test
    fun whenBalanceCardPlaceholderClicked_ThenOnClickIsNotTriggered() {
        var clicked = false

        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    BalanceCard(
                        value = BalanceCardType.Placeholder,
                        onClick = { clicked = true },
                    )
                }

            }

            with (BalanceCardTestTags) {
                onNodeWithTag(PLACEHOLDER).performClick()
                assertFalse(clicked)
            }
        }
    }

// endregion
}