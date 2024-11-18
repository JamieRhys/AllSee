package com.sycosoft.allsee.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sycosoft.allsee.presentation.components.accountaccesspage.AccessTokenRequestScreen
import com.sycosoft.allsee.presentation.viewmodels.AccountAccessPageViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountAccessPage(
    viewModel: AccountAccessPageViewModel,
) {
    var accessToken by remember { mutableStateOf("") }
    val response = viewModel.response.collectAsState()

    Scaffold {
        AccessTokenRequestScreen(
            accessToken = accessToken,
            onAccessTokenChange = { accessToken = it },
            onGetStartedButtonClick = { viewModel.saveToken(accessToken) },
            response = response.value,
        )
    }
}