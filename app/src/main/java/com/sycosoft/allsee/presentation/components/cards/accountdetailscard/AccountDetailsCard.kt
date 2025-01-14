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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun AccountDetailsCard(
    modifier: Modifier = Modifier,
    lightThemeColor: Color = PastelAqua,
    darkThemeColor: Color = DarkAqua,
    countryName: String,
    accountHolderName: String,
    accountNumber: String = "",
    sortCode: String = "",
    iban: String = "",
    bic: String = "",
) {
    val clipboardManager = LocalClipboardManager.current
    val copyString: String = if (accountNumber.isNotEmpty() && sortCode.isNotEmpty()) {
        stringResource(
            id = R.string.share_bank_details_text,
            "${stringResource(R.string.account_holder_name)}: $accountHolderName",
            "${stringResource(R.string.account_number)}: $accountNumber",
            "${stringResource(R.string.sort_code)}: $sortCode",
        )
    } else if (iban.isNotEmpty() && bic.isNotEmpty()) {
        stringResource(
            id = R.string.share_bank_details_text,
            "${stringResource(R.string.account_holder_name)}: $accountHolderName",
            "${stringResource(R.string.iban)}: $iban",
            "${stringResource(R.string.bic)}: $bic",
        )
    } else { "" }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
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
                text = accountHolderName,
                titleTestTag = AccountDetailsCardTestTags.TITLE_ACCOUNT_HOLDER_NAME,
                textTestTag = AccountDetailsCardTestTags.TEXT_ACCOUNT_HOLDER_NAME,
                buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_ACCOUNT_HOLDER_NAME,
                clipboardManager = clipboardManager,
            )
            HorizontalDivider()
            if (accountNumber.isNotEmpty()) {
                AccountDetailEntry(
                    title = stringResource(id = R.string.account_number),
                    text = accountNumber,
                    titleTestTag = AccountDetailsCardTestTags.TITLE_ACCOUNT_NUMBER,
                    textTestTag = AccountDetailsCardTestTags.TEXT_ACCOUNT_NUMBER,
                    buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_ACCOUNT_NUMBER,
                    clipboardManager = clipboardManager,
                )
                HorizontalDivider()
                AccountDetailEntry(
                    title = stringResource(id = R.string.sort_code),
                    text = sortCode,
                    titleTestTag = AccountDetailsCardTestTags.TITLE_SORT_CODE,
                    textTestTag = AccountDetailsCardTestTags.TEXT_SORT_CODE,
                    buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_SORT_CODE,
                    clipboardManager = clipboardManager,
                )
            } else if (iban.isNotEmpty() && bic.isNotEmpty()) {
                AccountDetailEntry(
                    title = stringResource(id = R.string.iban),
                    text = iban,
                    titleTestTag = AccountDetailsCardTestTags.TITLE_IBAN,
                    textTestTag = AccountDetailsCardTestTags.TEXT_IBAN,
                    buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_IBAN,
                    clipboardManager = clipboardManager,
                )
                HorizontalDivider()
                AccountDetailEntry(
                    title = stringResource(id = R.string.bic),
                    text = bic,
                    titleTestTag = AccountDetailsCardTestTags.TITLE_BIC,
                    textTestTag = AccountDetailsCardTestTags.TEXT_BIC,
                    buttonCopyTestTag = AccountDetailsCardTestTags.BUTTON_COPY_BIC,
                    clipboardManager = clipboardManager,
                )
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
                countryName = "UK",
                accountHolderName = "Joe Bloggs",
                accountNumber = "12345678",
                sortCode = "123456",
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
                accountHolderName = "Joe Bloggs",
                countryName = "International",
                iban = "GB12345678123456",
                bic = "1234567890",
            )
        }
    }
}