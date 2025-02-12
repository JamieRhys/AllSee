package com.sycosoft.allsee.presentation.components.cards.accountdetailscard

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.AnnotatedString
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

private typealias ADCTT = AccountDetailsCardTestTags

class AccountDetailsCardTest {
    @get:Rule val composeTestRule = createComposeRule()
    private val clipboardManager = mockk<ClipboardManager>(relaxed = true)
    private val slot = slot<AnnotatedString>()

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
    private val expectedAccountHolderDetailsFullCopyText = "$expectedTitleAccountHolderName: $expectedTextAccountHolderName"
    private val expectedSortCodeDetailsFullCopyText = "$expectedTitleSortCode: $expectedTextSortCode"
    private val expectedIBANDetailsFullCopyText = "$expectedTitleIBAN: $expectedTextIBAN"
    private val expectedBICDetailsFullCopyText = "$expectedTitleBIC: $expectedTextBIC"

// endregion

// region Default State

    @Test
    fun accountDetailsCardPlaceholderRendersCorrectly() {
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    AccountDetailsCard(
                        testTag = "testTag",
                        countryType = CountryType.UK,
                        value = AccountDetailsType.Placeholder,
                    )
                }
            }

            onNodeWithTag(ADCTT.PLACEHOLDER + "testTag").assertIsDisplayed()
        }
    }



    @Test
    fun accountDetailsCardRendersCorrectlyWhenProvidedUkValues() {
        with (composeTestRule) {
            setContent {
                AllSeeTheme {
                    AccountDetailsCard(
                        testTag = "testTag",
                        countryType = CountryType.UK,
                        value = AccountDetailsType.Value(
                            accountHolderName = expectedTextAccountHolderName,
                            accountNumber = expectedTextAccountNumber,
                            sortCode = expectedTextSortCode,
                            iban = expectedTextIBAN,
                            bic = expectedTextBIC,
                        )
                    )
                }
            }

            onNodeWithTag(ADCTT.PLACEHOLDER + "testTag").assertDoesNotExist()

            onNodeWithTag(ADCTT.FLAG_UK).assertIsDisplayed()
            onNodeWithTag(ADCTT.FLAG_INTERNATIONAL).assertIsNotDisplayed()

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
                        testTag = "testTag",
                        countryType = CountryType.INTERNATIONAL,
                        value = AccountDetailsType.Value(
                            accountHolderName = expectedTextAccountHolderName,
                            accountNumber = expectedTextAccountNumber,
                            sortCode = expectedTextSortCode,
                            iban = expectedTextIBAN,
                            bic = expectedTextBIC,
                        )
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
// region Share Button (UK)

    @Test
    fun whenShareButtonClicked_GivenUKValues_ThenCopyTextToClipboard() {
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.UK,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()

            verify(timeout = 1000) { clipboardManager.setText(any()) }

            assertEquals(AnnotatedString(expectedUKFullAccountDetailsCopyText), slot.captured)
        }
    }

    @Test
    fun whenShareButtonClicked_GivenMissingAccountNumber_ThenNoDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with(composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.UK,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = "",
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 0) { clipboardManager.setText(any()) }
        }
    }

    @Test
    fun whenShareButtonClicked_GivenMissingSortCode_ThenNoDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.UK,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = "",
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 0) { clipboardManager.setText(any()) }
        }
    }

// endregion
// region Share Button (International)

    @Test
    fun whenShareButtonClicked_GivenInternationalValues_ThenCopyTextToClipboard() {
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.INTERNATIONAL,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()

            verify(timeout = 1000) { clipboardManager.setText(any()) }

            assertEquals(AnnotatedString(expectedFullInternationalAccountDetailsCopyText), slot.captured)
        }
    }

    @Test
    fun whenShareButtonClicked_GivenMissingIBANNumber_ThenNoDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with(composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.INTERNATIONAL,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = "",
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 0) { clipboardManager.setText(any()) }
        }
    }

    @Test
    fun whenShareButtonClicked_GivenMissingBIC_ThenNoDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.INTERNATIONAL,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = "",
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_SHARE).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 0) { clipboardManager.setText(any()) }
        }
    }

// endregion
// region Entry Share Button

    @Test
    fun whenAccountHolderNameEntryShareButtonClicked_ThenAccountHolderNameEntryDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.UK,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_COPY_ACCOUNT_HOLDER_NAME).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            assertEquals(AnnotatedString(expectedAccountHolderDetailsFullCopyText), slot.captured)
        }
    }

    @Test
    fun whenAccountNumberEntryShareButtonClicked_ThenAccountNumberEntryDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.UK,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_COPY_SORT_CODE).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            assertEquals(AnnotatedString(expectedSortCodeDetailsFullCopyText), slot.captured)
        }
    }

    @Test
    fun whenSortCodeEntryShareButtonClicked_ThenSortCodeEntryDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.UK,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_COPY_SORT_CODE).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            assertEquals(AnnotatedString(expectedSortCodeDetailsFullCopyText), slot.captured)
        }
    }

    @Test
    fun whenIBANEntryShareButtonClicked_ThenIBANEntryDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.INTERNATIONAL,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_COPY_IBAN).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            assertEquals(AnnotatedString(expectedIBANDetailsFullCopyText), slot.captured)
        }
    }

    @Test
    fun whenBICEntryShareButtonClicked_ThenBICEntryDetailsCopied() {
        // Link the mock to use the slot
        every { clipboardManager.setText(capture(slot)) } just runs

        // When
        with (composeTestRule) {
            setContent {
                CompositionLocalProvider(LocalClipboardManager provides clipboardManager) {
                    AllSeeTheme {
                        AccountDetailsCard(
                            testTag = "testTag",
                            countryType = CountryType.INTERNATIONAL,
                            value = AccountDetailsType.Value(
                                accountHolderName = expectedTextAccountHolderName,
                                accountNumber = expectedTextAccountNumber,
                                sortCode = expectedTextSortCode,
                                iban = expectedTextIBAN,
                                bic = expectedTextBIC,
                            )
                        )
                    }
                }
            }

            onNodeWithTag(ADCTT.BUTTON_COPY_BIC).performClick()

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            assertEquals(AnnotatedString(expectedBICDetailsFullCopyText), slot.captured)
        }
    }

// endregion
// region Entry Long Press Click

    // TODO: Populate with tests to ensure entry long press works.

// endregion
}