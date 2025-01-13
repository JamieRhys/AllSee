package com.sycosoft.allsee.presentation.components.cards.accountdetailscard

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
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
                        .testTag(AccountDetailsCardTestTags.TEXT_TITLE)
                        .padding(start = 8.dp),
                    text = countryName,
                )
                Button(
                    modifier = Modifier.testTag(AccountDetailsCardTestTags.BUTTON_SHARE),
                    onClick = { /* TODO */ },
                    content = { Text("Share") }
                )
            }
        }
        Column {
            AccountDetailEntry(
                modifier = Modifier
                    .testTag(AccountDetailsCardTestTags.TEXT_ACCOUNT_HOLDER_NAME),
                title = stringResource(id = R.string.account_holder_name),
                text = accountHolderName,
            )
            HorizontalDivider()
            if (accountNumber.isNotEmpty()) {
                AccountDetailEntry(
                    modifier = Modifier.
                        testTag(AccountDetailsCardTestTags.TEXT_ACCOUNT_NUMBER),
                    title = stringResource(id = R.string.account_number),
                    text = accountNumber,
                )
                HorizontalDivider()
                AccountDetailEntry(
                    modifier = Modifier
                        .testTag(AccountDetailsCardTestTags.TEXT_SORT_CODE),
                    title = stringResource(id = R.string.sort_code),
                    text = sortCode,
                )
            } else if (iban.isNotEmpty() && bic.isNotEmpty()) {
                AccountDetailEntry(
                    modifier = Modifier.
                    testTag(AccountDetailsCardTestTags.TEXT_IBAN),
                    title = stringResource(id = R.string.iban),
                    text = iban,
                )
                HorizontalDivider()
                AccountDetailEntry(
                    modifier = Modifier
                        .testTag(AccountDetailsCardTestTags.TEXT_BIC),
                    title = stringResource(id = R.string.bic),
                    text = bic,
                )
            }
        }
    }
}

@Composable
private fun AccountDetailEntry(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
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
                text = title,
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                modifier = Modifier
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
            onClick = { /* TODO */ },
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