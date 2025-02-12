package com.sycosoft.allsee.presentation.components.cards.accountdetailscard

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sycosoft.allsee.R
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import com.sycosoft.allsee.presentation.theme.DarkAqua
import com.sycosoft.allsee.presentation.theme.PastelAqua
import com.sycosoft.allsee.presentation.theme.shimmerBackground

sealed interface AccountDetailsType {
    data object Placeholder : AccountDetailsType
    data class Value(
        val accountHolderName: String,
        val accountNumber: String,
        val sortCode: String,
        val iban: String,
        val bic: String,
    ) : AccountDetailsType
}

enum class CountryType {
    UK,
    INTERNATIONAL,
}

@Composable
fun AccountDetailsCard(
    testTag: String,
    lightThemeColor: Color = PastelAqua,
    darkThemeColor: Color = DarkAqua,
    countryType: CountryType,
    value: AccountDetailsType,
) {
    when (value) {
        is AccountDetailsType.Placeholder -> {
            Card(
                modifier = Modifier
                    .testTag(AccountDetailsCardTestTags.PLACEHOLDER + testTag)
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.5f)
                            .height(18.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .shimmerBackground(
                                colors = listOf(
                                    LocalContentColor.current.copy(alpha = 0.1f),
                                    LocalContentColor.current.copy(alpha = 0.05f),
                                    LocalContentColor.current.copy(alpha = 0.1f),
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .height(18.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .shimmerBackground(
                                colors = listOf(
                                    LocalContentColor.current.copy(alpha = 0.1f),
                                    LocalContentColor.current.copy(alpha = 0.05f),
                                    LocalContentColor.current.copy(alpha = 0.1f),
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .height(18.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                            .shimmerBackground(
                                colors = listOf(
                                    LocalContentColor.current.copy(alpha = 0.1f),
                                    LocalContentColor.current.copy(alpha = 0.05f),
                                    LocalContentColor.current.copy(alpha = 0.1f),
                                )
                            )
                    )
                }
            }
        }
        is AccountDetailsType.Value -> {
            val clipboardManager = LocalClipboardManager.current
            val countryName = when (countryType) {
                CountryType.UK -> stringResource(R.string.country_name_uk)
                CountryType.INTERNATIONAL -> stringResource(R.string.country_name_international)
            }
            val countryFlag = when (countryType) {
                CountryType.UK -> stringResource(id = R.string.flag_uk)
                CountryType.INTERNATIONAL -> stringResource(id = R.string.flag_international)
            }
            val countryFlagTestTag = when (countryType) {
                CountryType.UK -> AccountDetailsCardTestTags.FLAG_UK
                CountryType.INTERNATIONAL -> AccountDetailsCardTestTags.FLAG_INTERNATIONAL
            }
            val copyString: String = when (countryType) {
                CountryType.UK -> if (value.accountNumber.isNotEmpty() && value.sortCode.isNotEmpty()) { stringResource(
                    id = R.string.share_bank_details_text,
                    "${stringResource(R.string.account_holder_name)}: ${value.accountHolderName}",
                    "${stringResource(R.string.account_number)}: ${value.accountNumber}",
                    "${stringResource(R.string.sort_code)}: ${value.sortCode}",
                ) } else { "" }
                CountryType.INTERNATIONAL -> if (value.iban.isNotEmpty() && value.bic.isNotEmpty()) { stringResource(
                    id = R.string.share_bank_details_text,
                    "${stringResource(R.string.account_holder_name)}: ${value.accountHolderName}",
                    "${stringResource(R.string.iban)}: ${value.iban}",
                    "${stringResource(R.string.bic)}: ${value.bic}",
                ) } else { "" }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 8.dp,
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isSystemInDarkTheme()) darkThemeColor else lightThemeColor,
                            shape = RoundedCornerShape(
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp,
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier
                                .testTag(countryFlagTestTag)
                                .padding(start = 8.dp),
                            text = countryFlag,
                        )
                        Text(
                            modifier = Modifier
                                .testTag(AccountDetailsCardTestTags.TITLE_COUNTRY_NAME)
                                .padding(start = 8.dp),
                            text = countryName,
                        )
                        Button(
                            modifier = Modifier.testTag(AccountDetailsCardTestTags.BUTTON_SHARE),
                            onClick = {
                                if (copyString.isNotEmpty()) {
                                    clipboardManager.setText(AnnotatedString(copyString))
                                }
                            },
                            content = { Text("Share") }
                        )
                    }
                }
                Column {
                    AccountDetailEntry(
                        title = stringResource(id = R.string.account_holder_name),
                        text = value.accountHolderName,
                        titleTestTag = AccountDetailsCardTestTags.TITLE_ACCOUNT_HOLDER_NAME,
                        textTestTag = AccountDetailsCardTestTags.TEXT_ACCOUNT_HOLDER_NAME,
                        buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_ACCOUNT_HOLDER_NAME,
                        clipboardManager = clipboardManager,
                    )
                    HorizontalDivider()
                    when (countryType) {
                        CountryType.UK -> {
                            AccountDetailEntry(
                                title = stringResource(id = R.string.account_number),
                                text = value.accountNumber,
                                titleTestTag = AccountDetailsCardTestTags.TITLE_ACCOUNT_NUMBER,
                                textTestTag = AccountDetailsCardTestTags.TEXT_ACCOUNT_NUMBER,
                                buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_ACCOUNT_NUMBER,
                                clipboardManager = clipboardManager,
                            )
                            HorizontalDivider()
                            AccountDetailEntry(
                                title = stringResource(id = R.string.sort_code),
                                text = value.sortCode,
                                titleTestTag = AccountDetailsCardTestTags.TITLE_SORT_CODE,
                                textTestTag = AccountDetailsCardTestTags.TEXT_SORT_CODE,
                                buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_SORT_CODE,
                                clipboardManager = clipboardManager,
                            )
                        }
                        CountryType.INTERNATIONAL -> {
                            AccountDetailEntry(
                                title = stringResource(id = R.string.iban),
                                text = value.iban,
                                titleTestTag = AccountDetailsCardTestTags.TITLE_IBAN,
                                textTestTag = AccountDetailsCardTestTags.TEXT_IBAN,
                                buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_IBAN,
                                clipboardManager = clipboardManager,
                            )
                            HorizontalDivider()
                            AccountDetailEntry(
                                title = stringResource(id = R.string.bic),
                                text = value.bic,
                                titleTestTag = AccountDetailsCardTestTags.TITLE_BIC,
                                textTestTag = AccountDetailsCardTestTags.TEXT_BIC,
                                buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_BIC,
                                clipboardManager = clipboardManager,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AccountDetailEntry(
    modifier: Modifier = Modifier,
    titleTestTag: String,
    textTestTag: String,
    buttonCopyTestTag: String,
    title: String,
    text: String,
    clipboardManager: ClipboardManager,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    if (text.isNotEmpty() && title.isNotEmpty()) {
                        clipboardManager.setText(AnnotatedString("$title: $text"))
                    }
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 8.dp
                ),
        ) {
            Text(
                modifier = Modifier.testTag(titleTestTag),
                text = title,
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                modifier = Modifier
                    .testTag(textTestTag)
                    .padding(
                        top = 4.dp,
                        bottom = 4.dp,
                        start = 8.dp
                    ),
                text = text,
                style = TextStyle(
                    fontSize = 24.sp,
                )
            )
        }
        IconButton(
            modifier = Modifier
                .testTag(buttonCopyTestTag),
            onClick = {
                if (text.isNotEmpty() && title.isNotEmpty()) {
                    clipboardManager.setText(AnnotatedString("$title: $text"))
                }
            },
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = null,
            )
        }
    }
}

@Preview(name = "Light Mode")
@Composable
private fun LM_AccountDetailsCardPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccountDetailsCard(
                testTag = "",
                countryType = CountryType.UK,
                value = AccountDetailsType.Value(
                    accountHolderName = "Joe Bloggs",
                    accountNumber = "12345678",
                    sortCode = "123456",
                    iban = "",
                    bic = "",
                )
            )
        }
    }
}

@Preview(name = "Light Mode (Placeholder)")
@Composable
private fun LM_AccountDetailsCardPlaceholderPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccountDetailsCard(
                testTag = "",
                countryType = CountryType.UK,
                value = AccountDetailsType.Placeholder,
            )
        }
    }
}

@Preview(name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DM_AccountDetailsCardPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccountDetailsCard(
                testTag = "",
                countryType = CountryType.INTERNATIONAL,
                value = AccountDetailsType.Value(
                    accountHolderName = "Joe Bloggs",
                    accountNumber = "",
                    sortCode = "",
                    iban = "GB2L56754327123456",
                    bic = "23554671",
                )
            )
        }
    }
}

@Preview(name = "Dark Mode (Placeholder)",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DM_AccountDetailsCardPlaceholderPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            AccountDetailsCard(
                testTag = "",
                countryType = CountryType.INTERNATIONAL,
                value = AccountDetailsType.Placeholder,
            )
        }
    }
}