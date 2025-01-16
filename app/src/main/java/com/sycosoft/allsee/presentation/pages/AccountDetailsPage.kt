package com.sycosoft.allsee.presentation.pages

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sycosoft.allsee.presentation.components.screens.accountdetailspage.AccountDetailsPageScreen
import com.sycosoft.allsee.presentation.models.AccountDetails
import com.sycosoft.allsee.presentation.utils.UiState
import com.sycosoft.allsee.presentation.viewmodels.AccountDetailsPageViewModel

@Composable
fun AccountDetailsPage(
    viewModel: AccountDetailsPageViewModel,
    onNavigateBack: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    when (val state = viewState.accountDetailsState) {
        is UiState.Success<AccountDetails> -> {
            AccountDetailsPageScreen(
                name = state.data.name,
                type = state.data.type,
                accountNumber = state.data.accountNumber,
                sortCode = state.data.sortCode,
                iban = state.data.iban,
                bic = state.data.bic,
                onBackButtonClick = remember { onNavigateBack }
            )
        }
        is UiState.Error -> {
            // TODO: Implement Error Screen.
        }
        UiState.Loading -> {
            // TODO: Implement Loading Screen.
            Log.d("AccountDetailsPage", "Loading...")
        }
        else -> {}
    }
}