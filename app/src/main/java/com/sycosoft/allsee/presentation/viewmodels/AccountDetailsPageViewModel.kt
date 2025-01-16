package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.usecases.GetAccountsUseCase
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.presentation.models.AccountDetails
import com.sycosoft.allsee.presentation.usecases.GetAccountDetailsUseCase
import com.sycosoft.allsee.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountDetailsPageViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getAccountDetailsUseCase: GetAccountDetailsUseCase,
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {
    data class ViewState(
        val accountDetailsState: UiState<AccountDetails>
    )
    private val accountDetailsState = MutableStateFlow<UiState<AccountDetails>>(value = UiState.Loading)

    // Using Map instead of combine as this is a single Flow and not multiple.
    val viewState: StateFlow<ViewState> = accountDetailsState.map {
        ViewState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ViewState(
            accountDetailsState = accountDetailsState.value
        )
    )

    init {
        viewModelScope.launch {
            getAccountDetails()
        }
    }

    private suspend fun getAccountDetails() = try {
        accountDetailsState.update {
            UiState.Success(
                getAccountDetailsUseCase(
                    account = getAccountsUseCase().first(),
                    person = getPersonUseCase()
                )
            )
        }
    } catch (e: RepositoryException) {
        UiState.Error(e.error.error, e.error.errorDescription)
    }
}