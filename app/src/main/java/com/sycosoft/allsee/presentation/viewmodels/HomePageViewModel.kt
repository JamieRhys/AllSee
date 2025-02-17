package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.models.types.BalanceType
import com.sycosoft.allsee.domain.usecases.GetAccountsUseCase
import com.sycosoft.allsee.domain.usecases.GetBalanceUseCase
import com.sycosoft.allsee.presentation.components.cards.balancecard.BalanceCardType
import com.sycosoft.allsee.presentation.components.text.DynamicTextType
import com.sycosoft.allsee.presentation.usecases.FormatBalanceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
) : ViewModel() {
    data class ViewState(
        val clearedBalance: BalanceCardType,
        val accountName: DynamicTextType,
    )

    private val initialViewState = ViewState(
        clearedBalance = BalanceCardType.Placeholder,
        accountName = DynamicTextType.Placeholder,
    )
    private val _viewState = MutableStateFlow(initialViewState)
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            val accountType = getAccountsUseCase().first().name
            val clearedBalance = getBalanceUseCase(BalanceType.CLEARED_BALANCE).minorUnits

            _viewState.value = viewState.value.copy(
                clearedBalance = BalanceCardType.Value(clearedBalance = FormatBalanceUseCase().invoke(clearedBalance)),
                accountName = DynamicTextType.Value(text = accountType),
            )
        }
    }
}