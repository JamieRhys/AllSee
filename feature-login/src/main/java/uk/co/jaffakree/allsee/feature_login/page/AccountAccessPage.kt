package uk.co.jaffakree.allsee.feature_login.page

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
import uk.co.jaffakree.allsee.feature_login.models.NameAndAccountType
import uk.co.jaffakree.allsee.feature_login.components.dialogs.UserConfirmationDialog
import uk.co.jaffakree.allsee.feature_login.screen.AccessTokenRequestScreen
import uk.co.jaffakree.allsee.core.ui.utils.UiState
import uk.co.jaffakree.allsee.feature_login.viewmodel.AccountAccessPageViewModel

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
                    onDismissButtonClick = remember { viewModel::resetLoadingState },
                    onConfirmButtonClick = remember { { onNavigateToHomePage() } },
                    onDismissRequest = remember { viewModel::resetLoadingState }
                )

            is UiState.Error ->
                LaunchedEffect(key1 = Unit) {
                    snackbarHostState.showSnackbar(state.errorDescription)
                }

            else -> {}
        }
    }
}