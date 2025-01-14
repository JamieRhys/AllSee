package com.sycosoft.allsee.presentation.components.cards.accountdetailscard

import android.content.Context
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.AnnotatedString
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

private typealias ADCTT = AccountDetailsCardTestTags

class AccountDetailsCardTest {
    @get:Rule val composeTestRule = createComposeRule()

// region Expected Variables

    private val expectedCountryNameUK = "UK"
    private val expectedCountryNameInt = "International"
    private val expectedTextAccountHolderName = "Joe Bloggs"
    private val expectedTextAccountNumber = "12345678"
    private val expectedTextIBAN = "GB2LC12345612345678"
    private val expectedTextBIC = "1234567890"
    private val expectedTextSortCode = "123456"
    private val expectedTextShare = "Share"
    private val expectedTitleAccountHolderName = "Account Holder Name"
    private val expectedTitleAccountNumber = "Account Number"
    private val expectedTitleIBAN = "IBAN"
    private val expectedTitleBIC = "BIC"
    private val expectedTitleSortCode = "Sort Code"
    private val expectedFullInternationalAccountDetailsCopyText = "Hey, here's my bank details.\n$expectedTitleAccountHolderName: $expectedTextAccountHolderName,\n$expectedTitleIBAN: $expectedTextIBAN,\n$expectedTitleBIC: $expectedTextBIC"
    private val expectedUKFullAccountDetailsCopyText = "Hey, here's my bank details.\n$expectedTitleAccountHolderName: $expectedTextAccountHolderName,\n$expectedTitleAccountNumber: $expectedTextAccountNumber,\n$expectedTitleSortCode: $expectedTextSortCode"

// endregion

// region Default State

    @Test
    fun accountDetailsCardRendersCorrectlyWhenProvidedUkValues() {
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    AccountDetailsCard(
                        countryName = expectedCountryNameUK,
                        accountHolderName = expectedTextAccountHolderName,
                        accountNumber = expectedTextAccountNumber,
                        sortCode = expectedTextSortCode,
                    )
                }
            }

            onNodeWithTag(ADCTT.TITLE_COUNTRY_NAME).assertIsDisplayed().assertTextEquals(expectedCountryNameUK)
            onNodeWithTag(ADCTT.BUTTON_SHARE).assertIsDisplayed().assertTextEquals(expectedTextShare)

            onNodeWithTag(ADCTT.TITLE_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleAccountHolderName)
            onNodeWithTag(ADCTT.TEXT_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextAccountHolderName)
            onNodeWithTag(ADCTT.BUTTON_COPY_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()

            onNodeWithTag(ADCTT.TITLE_ACCOUNT_NUMBER, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleAccountNumber)
            onNodeWithTag(ADCTT.TEXT_ACCOUNT_NUMBER, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextAccountNumber)
            onNodeWithTag(ADCTT.BUTTON_COPY_ACCOUNT_NUMBER, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()

            onNodeWithTag(ADCTT.TITLE_SORT_CODE, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleSortCode)
            onNodeWithTag(ADCTT.TEXT_SORT_CODE, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextSortCode)
            onNodeWithTag(ADCTT.BUTTON_COPY_SORT_CODE, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()
        }
    }

    @Test
    fun accountDetailsCardRendersCorrectlyWhenProvidedInternationalValues() {
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    AccountDetailsCard(
                        countryName = expectedCountryNameInt,
                        accountHolderName = expectedTextAccountHolderName,
                        iban = expectedTextIBAN,
                        bic = expectedTextBIC,
                    )
                }
            }

            onNodeWithTag(ADCTT.TITLE_COUNTRY_NAME).assertIsDisplayed().assertTextEquals(expectedCountryNameInt)
            onNodeWithTag(ADCTT.BUTTON_SHARE).assertIsDisplayed().assertTextEquals(expectedTextShare)

            onNodeWithTag(ADCTT.TITLE_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleAccountHolderName)
            onNodeWithTag(ADCTT.TEXT_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextAccountHolderName)

            onNodeWithTag(ADCTT.TITLE_IBAN, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleIBAN)
            onNodeWithTag(ADCTT.TEXT_IBAN, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextIBAN)
            onNodeWithTag(ADCTT.BUTTON_COPY_IBAN, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()

            onNodeWithTag(ADCTT.TITLE_BIC, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleBIC)
            onNodeWithTag(ADCTT.TEXT_BIC, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextBIC)
            onNodeWithTag(ADCTT.BUTTON_COPY_BIC, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()
        }
    }

// endregion
// region Share Button

    @Test
    fun whenShareButtonClicked_GivenUKValues_ThenCopyTextToClipboard() {
        var actual = ""
        // When
        with (composeTestRule) {
            setContent {
                // Clear clipboard prior to test to ensure any previous tests does not cause issues.
                LocalClipboardManager.current.setText(AnnotatedString(""))

                AllSeeTheme {
                    AccountDetailsCard(
                        countryName = expectedCountryNameUK,
                        accountHolderName = expectedTextAccountHolderName,
                        accountNumber = expectedTextAccountNumber,
                        sortCode = expectedTextSortCode,
                    )
                }
                actual = LocalClipboardManager.current.getText().toString()
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()
            assertEquals(expectedUKFullAccountDetailsCopyText, actual)
        }
    }

    @Test
    fun whenShareButtonClicked_GivenMissingAccountNumber_ThenNoDetailsCopied() {
        val expected = ""
        var actual = ""
        // When
        with (composeTestRule) {
            setContent {
                // Clear clipboard prior to test to ensure any previous tests does not cause issues.
                LocalClipboardManager.current.setText(AnnotatedString(""))

                AllSeeTheme {
                    AccountDetailsCard(
                        countryName = expectedCountryNameUK,
                        accountHolderName = expectedTextAccountHolderName,
                        sortCode = expectedTextSortCode,
                    )
                }
                actual = LocalClipboardManager.current.getText().toString()
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()
            waitForIdle()
            assertEquals(expected, actual)
        }
    }

// endregion
}