package com.sycosoft.allsee.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.sycosoft.allsee.R
import com.sycosoft.allsee.ui.theme.AllSeeTheme

@Composable
fun AccountAccessPage() {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(R.string.aap_initial_greeting))
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.aap_get_started)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Access Token") }
            )
            Button(
                onClick = {},
            ) {
                Text(text = stringResource(R.string.button_get_started))
            }
        }
    }
}

@Preview(name = "Light Mode", showSystemUi = true, showBackground = true)
@Composable
private fun LM_AccountAccessPagePreview() {
    AllSeeTheme {
        Surface {
            AccountAccessPage()
        }
    }
}

@Preview(name = "Dark Mode", showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:width=1080px,height=2340px,dpi=440"
)
@Composable
private fun DM_AccountAccessPagePreview() {
    AllSeeTheme {
        Surface {
            AccountAccessPage()
        }
    }
}