package com.sycosoft.allsee.ui.screens

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Configuration
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.sycosoft.allsee.domain.network.ConnectivityObserver
import com.sycosoft.allsee.ui.components.InternetLostScreen
import com.sycosoft.allsee.ui.components.accountaccesspage.AccessTokenRequestScreen
import com.sycosoft.allsee.ui.theme.AllSeeTheme
import com.sycosoft.allsee.ui.viewmodels.AccountAccessPageViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountAccessPage(
    viewModel: AccountAccessPageViewModel,
) {
    var accessToken by remember { mutableStateOf("") }

    val networkStatus by viewModel.networkStatus.collectAsState()

    Scaffold {
        when (networkStatus) {
            ConnectivityObserver.Status.Available, ConnectivityObserver.Status.Losing -> {
                AccessTokenRequestScreen(
                    accessToken = accessToken,
                    onAccessTokenChange = { accessToken = it },
                    onGetStartedButtonClick = { /*TODO*/ },
                )
            }
            ConnectivityObserver.Status.Unavailable,ConnectivityObserver.Status.Lost -> {
                InternetLostScreen()
            }
        }

    }
}


@Preview(name = "Light Mode", showSystemUi = true, showBackground = true)
@Composable
private fun LM_AccountAccessPagePreview() {
    AllSeeTheme {
        Surface {
            AccountAccessPage(
                viewModel = AccountAccessPageViewModel(application = Application())
            )
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
            AccountAccessPage(viewModel = AccountAccessPageViewModel(application = Application()))
        }
    }
}