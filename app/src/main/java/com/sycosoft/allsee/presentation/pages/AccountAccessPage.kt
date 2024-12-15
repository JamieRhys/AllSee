package com.sycosoft.allsee.presentation.pages

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val accessToken = viewModel.accessToken.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val showProgressBar = remember { mutableStateOf(false) }
    val openConfirmationDialog = remember { mutableStateOf(false) }

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
            accessToken = accessToken.value,
            onAccessTokenChange = { viewModel.updateAccessToken(it) },
            showProgressBar = showProgressBar.value,
            onButtonClick = { viewModel.saveToken() },
        )
        when (val state = loadingState.value) {
            is UiState.Initial -> {
                showProgressBar.value = false
            }
            is UiState.Loading -> {
                showProgressBar.value = true
            }
            is UiState.Success -> {
                showProgressBar.value = false
                openConfirmationDialog.value = true
            }
            is UiState.Error -> {
                showProgressBar.value = false
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(state.errorDescription)
                }
            }
        }
        when {
            openConfirmationDialog.value -> {
                // Given this dialog is open, it should be safe to say that we have successfully been given
                // the Success UiState. We should be able to cast it to the correct object we need going forward.
                val nameAndAccountType = loadingState.value as UiState.Success<NameAndAccountType>

                UserConfirmationDialog(
                    name = nameAndAccountType.data.name,
                    accountType = nameAndAccountType.data.type,
                    onDismissButtonClick = {
                        openConfirmationDialog.value = false
                        viewModel.resetLoadingState()
                    },
                    onConfirmButtonClick = {
                        onNavigateToHomePage()
                    },
                    onDismissRequest = {
                        openConfirmationDialog.value = false
                        viewModel.resetLoadingState()
                    }
                )
            }
        }
    }
}