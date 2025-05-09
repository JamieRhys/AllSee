package uk.co.jaffakree.allsee.login.presentation.page

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.jaffakree.allsee.login.presentation.screen.LoginScreen
import uk.co.jaffakree.allsee.login.presentation.viewmodel.LoginPageViewModel

@Composable
fun LoginPage(
    viewModel: LoginPageViewModel,
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    LoginScreen(
        accessToken = viewState.value.accessToken,
        onAccessTokenChange = { viewModel.updateAccessToken(it) },
        showProgressBar = viewState.value.showUserConfirmationDialog,
        onLoginButtonClick = {}
    )
}