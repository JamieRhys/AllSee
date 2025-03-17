package uk.co.jaffakree.allsee.feature_accountdetails.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.jaffakree.allsee.feature_accountdetails.screen.AccountDetailsPageScreen
import uk.co.jaffakree.allsee.feature_accountdetails.viewmodel.AccountDetailsPageViewModel

@Composable
fun AccountDetailsPage(
    viewModel: AccountDetailsPageViewModel,
    onNavigateBack: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    AccountDetailsPageScreen(
        type = "Individual", // TODO
        accountDetailsType = viewState.value.accountDetails,
        onBackButtonClick = remember { onNavigateBack }
    )

//    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
//
//    when (val state = viewState.accountDetailsState) {
//        is UiState.Success<AccountDetails> -> {
//            AccountDetailsPageScreen(
//                name = state.data.name,
//                type = state.data.type,
//                accountNumber = state.data.accountNumber,
//                sortCode = state.data.sortCode,
//                iban = state.data.iban,
//                bic = state.data.bic,
//                onBackButtonClick = remember { onNavigateBack }
//            )
//        }
//        is UiState.Error -> {
//            // TODO: Implement Error Screen.
//        }
//        UiState.Loading -> {
//            // TODO: Implement Loading Screen.
//            Log.d("AccountDetailsPage", "Loading...")
//        }
//        else -> {}
//    }
}