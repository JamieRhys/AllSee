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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sycosoft.allsee.R
import com.sycosoft.allsee.ui.theme.AllSeeTheme
import com.sycosoft.allsee.ui.viewmodels.AccountAccessPageViewModel
import java.io.File

@Composable
fun AccountAccessPage(viewModel: AccountAccessPageViewModel) {
    var token by remember { mutableStateOf("") }



    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = token,
                onValueChange = { token = it },
                placeholder = { Text(text = stringResource(id = R.string.aap_access_token)) }
            )
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { },
            ) {
                Text(text = stringResource(id = R.string.button_get_started))
            }
        }
    }
}


@Preview(name = "Light Mode", showSystemUi = true, showBackground = true)
@Composable
private fun LM_AccountAccessPagePreview() {
    AllSeeTheme {
        Surface {
            AccountAccessPage(viewModel = AccountAccessPageViewModel(filesDir = File("/")))
        }
    }
}

@Preview(name = "Dark Mode", showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,

)
@Composable
private fun DM_AccountAccessPagePreview() {
    AllSeeTheme {
        Surface {
            AccountAccessPage(viewModel = AccountAccessPageViewModel(filesDir = File("/")))
        }
    }
}