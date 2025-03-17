package uk.co.jaffakree.allsee.feature_accountdetails.components.cards.accountdetailscard

import android.content.Context
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
import androidx.test.core.app.ApplicationProvider
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import uk.co.jaffakree.allsee.core.ui.theme.AllSeeTheme
import uk.co.jaffakree.allsee.feature_accountdetails.R

class AccountDetailsCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val clipboardManager = mockk<ClipboardManager>(relaxed = true)
    private val slot = slot<AnnotatedString>()
    private val context = ApplicationProvider.getApplicationContext<Context>()

// region Expected Variables


    private val expectedTextAccountHolderName = "Joe Bloggs"
    private val expectedTextAccountNumber = "12345678"
    private val expectedTextIBAN = "GB2LC12345612345678"
    private val expectedTextBIC = "1234567890"
    private val expectedTextSortCode = "123456"
    private val expectedTextShare = context.getString(R.string.button_share)
    private val expectedTitleAccountHolderName = context.getString(R.string.account_holder_name)
    private val expectedTitleAccountNumber = context.getString(R.string.account_number)
    private val expectedTitleIBAN = context.getString(R.string.iban)
    private val expectedTitleBIC = context.getString(R.string.bic)
    private val expectedTitleSortCode = context.getString(R.string.sort_code)
    private val expectedFullInternationalAccountDetailsCopyText = context.getString(
        R.string.share_bank_details_text,
        "$expectedTitleAccountHolderName: $expectedTextAccountHolderName",
        "$expectedTitleIBAN: $expectedTextIBAN",
        "$expectedTitleBIC: $expectedTextBIC",
    )
    private val expectedUKFullAccountDetailsCopyText = context.getString(
        R.string.share_bank_details_text,
        "$expectedTitleAccountHolderName: $expectedTextAccountHolderName",
        "$expectedTitleAccountNumber: $expectedTextAccountNumber",
        "$expectedTitleSortCode: $expectedTextSortCode",
    )
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(PLACEHOLDER + "testTag").assertIsDisplayed()
            }
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(PLACEHOLDER + "testTag").assertDoesNotExist()

                onNodeWithTag(FLAG_UK).assertIsDisplayed()
                onNodeWithTag(FLAG_INTERNATIONAL).assertIsNotDisplayed()

                onNodeWithTag(TITLE_COUNTRY_NAME).assertIsDisplayed().assertTextEquals(context.getString(R.string.country_name_uk))
                onNodeWithTag(BUTTON_SHARE).assertIsDisplayed().assertTextEquals(expectedTextShare)

                onNodeWithTag(TITLE_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleAccountHolderName)
                onNodeWithTag(TEXT_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextAccountHolderName)
                onNodeWithTag(BUTTON_COPY_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()

                onNodeWithTag(TITLE_ACCOUNT_NUMBER, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleAccountNumber)
                onNodeWithTag(TEXT_ACCOUNT_NUMBER, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextAccountNumber)
                onNodeWithTag(BUTTON_COPY_ACCOUNT_NUMBER, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()

                onNodeWithTag(TITLE_SORT_CODE, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleSortCode)
                onNodeWithTag(TEXT_SORT_CODE, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextSortCode)
                onNodeWithTag(BUTTON_COPY_SORT_CODE, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()
            }
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(TITLE_COUNTRY_NAME).assertIsDisplayed().assertTextEquals(context.getString(R.string.country_name_international))
                onNodeWithTag(BUTTON_SHARE).assertIsDisplayed().assertTextEquals(expectedTextShare)

                onNodeWithTag(TITLE_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleAccountHolderName)
                onNodeWithTag(TEXT_ACCOUNT_HOLDER_NAME, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextAccountHolderName)

                onNodeWithTag(TITLE_IBAN, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleIBAN)
                onNodeWithTag(TEXT_IBAN, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextIBAN)
                onNodeWithTag(BUTTON_COPY_IBAN, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()

                onNodeWithTag(TITLE_BIC, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTitleBIC)
                onNodeWithTag(TEXT_BIC, useUnmergedTree = true).assertIsDisplayed().assertTextEquals(expectedTextBIC)
                onNodeWithTag(BUTTON_COPY_BIC, useUnmergedTree = true).assertIsDisplayed().assertIsEnabled()
            }
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_SHARE).performClick()
            }

            verify(timeout = 1000) { clipboardManager.setText(any()) }

            TestCase.assertEquals(
                AnnotatedString(expectedUKFullAccountDetailsCopyText),
                slot.captured
            )
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_SHARE).performClick()
            }

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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_SHARE).performClick()
            }

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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_SHARE).performClick()
            }

            verify(timeout = 1000) { clipboardManager.setText(any()) }

            TestCase.assertEquals(
                AnnotatedString(expectedFullInternationalAccountDetailsCopyText),
                slot.captured
            )
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_SHARE).performClick()
            }

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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_SHARE).performClick()
            }

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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_COPY_ACCOUNT_HOLDER_NAME).performClick()
            }

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            TestCase.assertEquals(
                AnnotatedString(expectedAccountHolderDetailsFullCopyText),
                slot.captured
            )
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_COPY_SORT_CODE).performClick()
            }

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            TestCase.assertEquals(
                AnnotatedString(expectedSortCodeDetailsFullCopyText),
                slot.captured
            )
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_COPY_SORT_CODE).performClick()
            }

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            TestCase.assertEquals(
                AnnotatedString(expectedSortCodeDetailsFullCopyText),
                slot.captured
            )
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_COPY_IBAN).performClick()
            }

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            TestCase.assertEquals(AnnotatedString(expectedIBANDetailsFullCopyText), slot.captured)
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

            with (AccountDetailsCardTestTags) {
                onNodeWithTag(BUTTON_COPY_BIC).performClick()
            }

            // Verify the setText method was not called. If so, there's no need to check if the
            // clipboard content has changed because it shouldn't have by this.
            verify(exactly = 1) { clipboardManager.setText(any()) }

            // Make sure that the clipboard equals that of the expected text.
            TestCase.assertEquals(AnnotatedString(expectedBICDetailsFullCopyText), slot.captured)
        }
    }

// endregion
// region Entry Long Press Click

    // TODO: Populate with tests to ensure entry long press works.

// endregion
}