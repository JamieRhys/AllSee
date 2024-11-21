package com.sycosoft.allsee.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val loadingState = viewModel.loadingState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    )
                }
            )
        }
    ) {
        AccessTokenRequestScreen(
            accessToken = accessToken,
            onAccessTokenChange = { accessToken = it },
            onGetStartedButtonClick = { viewModel.saveToken(accessToken) },
            uiState = loadingState.value,
            errorSnackbarCallback = { errorString ->
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(errorString)
                }
            }
        )
    }
}