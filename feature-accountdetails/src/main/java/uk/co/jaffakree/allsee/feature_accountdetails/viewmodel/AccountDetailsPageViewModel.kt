package uk.co.jaffakree.allsee.feature_accountdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.jaffakree.allsee.core.ui.utils.UiState
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.usecases.GetAccountsUseCase
import uk.co.jaffakree.allsee.domain.usecases.GetPersonUseCase
import uk.co.jaffakree.allsee.feature_accountdetails.components.cards.accountdetailscard.AccountDetailsType
import uk.co.jaffakree.allsee.feature_accountdetails.usecases.GetAccountDetailsUseCase
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