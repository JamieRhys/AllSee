package com.sycosoft.allsee.presentation.components.cards.balancecard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sycosoft.allsee.R
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import com.sycosoft.allsee.presentation.theme.Typography

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier,
    clearedBalance: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .clickable(
                onClickLabel = stringResource(id = R.string.button_add_funds),
                onClick = { onClick() }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 15.dp,
                    end = 15.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.testTag(BalanceCardTestTags.BALANCE),
                text = clearedBalance,
                style = TextStyle(
                    color = Typography.bodyLarge.color,
                    fontSize = 24.sp,
                    fontFamily = Typography.bodyLarge.fontFamily,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    modifier = Modifier.testTag(BalanceCardTestTags.ADD_FUNDS_ICON),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.button_add_funds),
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Composable
private fun LM_BalanceCardPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            BalanceCard(
                clearedBalance = "£1,000",
                onClick = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    name = "Dark Mode"
)
@Composable
private fun DM_BalanceCardPreview() {
    AllSeeTheme(dynamicColor = false) {
        Surface {
            BalanceCard(
                clearedBalance = "£1,000",
                onClick = {},
            )
        }
    }
}