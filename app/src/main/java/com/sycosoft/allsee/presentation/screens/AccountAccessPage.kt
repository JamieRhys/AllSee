package com.sycosoft.allsee.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.sycosoft.allsee.R
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.presentation.components.accountaccesspage.AccessTokenRequestScreen
import com.sycosoft.allsee.presentation.utils.UiState
import com.sycosoft.allsee.presentation.viewmodels.AccountAccessPageViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountAccessPage(
    viewModel: AccountAccessPageViewModel,
) {
    val accessToken = viewModel.accessToken.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
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
            onGetStartedButtonClick = { viewModel.saveToken() },
            uiState = loadingState.value,
            errorSnackBarCallback = { errorString ->
                LaunchedEffect(snackbarHostState) {
                    snackbarHostState.showSnackbar(errorString)
                }
            },
            onOpenConfirmationDialog = { openConfirmationDialog.value = it },
        )
        when {
            openConfirmationDialog.value -> {
                // Given this dialog is open, it should be safe to say that we have successfully been given
                // the Success UiState. We should be able to cast it to the correct object we need going forward.
                val nameAndAccountType = loadingState.value as UiState.Success<NameAndAccountType>

                AlertDialog(
                    text = {
                        Text(text = stringResource(id = R.string.adt_identity_confirmation, nameAndAccountType.data.name, nameAndAccountType.data.type))
                    },
                    onDismissRequest = {openConfirmationDialog.value = false },
                    confirmButton = {
                        // TODO: Navigate to main screen when confirmed (once implemented) for now, just dismiss.
                        Button(onClick = { openConfirmationDialog.value = false }) {
                            Text(text = "Yes")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { openConfirmationDialog.value = false}) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}