package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.BalanceType
import com.sycosoft.allsee.domain.usecases.GetAccountsUseCase
import com.sycosoft.allsee.domain.usecases.GetBalanceUseCase
import com.sycosoft.allsee.domain.usecases.GetFullBalanceUseCase
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getFullBalanceUseCase: GetFullBalanceUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
) : ViewModel() {
    private val _personDetails = MutableStateFlow<Person?>(null)
    val personDetails: StateFlow<Person?> = _personDetails

    private val uiState: MutableStateFlow<UiState<Nothing>> = MutableStateFlow(UiState.Loading)
    private val accountName: MutableStateFlow<String> = MutableStateFlow("")
    private val clearedBalance: MutableStateFlow<Int> = MutableStateFlow(0)

    val viewState: StateFlow<ViewState> by lazy {
        combine(
            uiState,
            accountName,
            clearedBalance,
            ::ViewState
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ViewState(
                uiState = uiState.value,
                accountName = accountName.value,
                clearedBalance = clearedBalance.value
            )
        )
    }

    init {
        viewModelScope.launch {
            _personDetails.value = getPersonUseCase()
            getAccountType()
            getClearedBalance()
            println()
        }
    }

    private suspend fun getAccountType() { accountName.value = getAccountsUseCase().first().name }

    private suspend fun getClearedBalance() { clearedBalance.value = getBalanceUseCase(BalanceType.CLEARED_BALANCE).minorUnits }

    data class ViewState(
        val uiState: UiState<Nothing>,
        val accountName: String,
        val clearedBalance: Int
    )
}