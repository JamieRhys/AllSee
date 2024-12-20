package com.sycosoft.allsee.presentation.pages

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.presentation.components.dialogs.UserConfirmationDialog
import com.sycosoft.allsee.presentation.components.screens.accountaccesspage.AccessTokenRequestScreen
import com.sycosoft.allsee.presentation.utils.UiState
import com.sycosoft.allsee.presentation.viewmodels.AccountAccessPageViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountAccessPage(
    viewModel: AccountAccessPageViewModel,
    onNavigateToHomePage: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
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
            accessToken = viewState.accessToken,
            onAccessTokenChange = remember { viewModel::updateAccessToken },
            showProgressBar = viewState.nameAndAccountState is UiState.Loading,
            onButtonClick = remember { viewModel::saveToken },
        )

        when (val state = viewState.nameAndAccountState) {

            is UiState.Success<NameAndAccountType> ->
                UserConfirmationDialog(
                    name = state.data.name,
                    accountType = state.data.type,
                    onDismissButtonClick = { viewModel.resetLoadingState() },
                    onConfirmButtonClick = { onNavigateToHomePage() },
                    onDismissRequest = { viewModel.resetLoadingState() }
                )

            is UiState.Error ->
                LaunchedEffect(key1 = Unit) {
                    snackbarHostState.showSnackbar(state.errorDescription)
                }

            else -> {}
        }
    }
}