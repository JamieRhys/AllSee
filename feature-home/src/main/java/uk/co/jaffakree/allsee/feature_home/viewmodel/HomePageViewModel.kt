package uk.co.jaffakree.allsee.feature_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.jaffakree.allsee.core.ui.components.text.DynamicTextType
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import uk.co.jaffakree.allsee.domain.usecases.FormatBalanceUseCase
import uk.co.jaffakree.allsee.domain.usecases.GetAccountsUseCase
import uk.co.jaffakree.allsee.domain.usecases.GetBalanceUseCase
import uk.co.jaffakree.allsee.domain.usecases.GetRecentFeedUseCase
import uk.co.jaffakree.allsee.feature_home.components.balancecard.BalanceCardType
import javax.inject.Inject

class HomePageViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getRecentFeedUseCase: GetRecentFeedUseCase,
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

            getRecentFeedUseCase()
        }
    }
}