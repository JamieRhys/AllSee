package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.usecases.GetAccountsUseCase
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.presentation.components.cards.accountdetailscard.AccountDetailsType
import com.sycosoft.allsee.presentation.usecases.GetAccountDetailsUseCase
import com.sycosoft.allsee.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountDetailsPageViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {
    data class ViewState(
        val accountDetails: AccountDetailsType,
    )

    private val _initialViewState = ViewState(accountDetails = AccountDetailsType.Placeholder)
    private val _viewState = MutableStateFlow(_initialViewState)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccountDetails()
        }
    }

    private suspend fun getAccountDetails() = try {
        val accountDetails = getAccountDetailsUseCase(
            account = getAccountsUseCase().first(),
            person = getPersonUseCase()
        )

        _viewState.value = _viewState.value.copy(
            accountDetails = AccountDetailsType.Value(
                accountHolderName = accountDetails.name,
                accountNumber = accountDetails.accountNumber,
                sortCode = accountDetails.sortCode,
                iban = accountDetails.iban,
                bic = accountDetails.bic,
            )
        )
    } catch (e: RepositoryException) {
        UiState.Error(e.error.error, e.error.errorDescription)
    }
}