package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.usecases.GetAccountHolderNameUseCase
import com.sycosoft.allsee.domain.usecases.GetAccountHolderUseCase
import com.sycosoft.allsee.domain.usecases.SaveTokenUseCase
import com.sycosoft.allsee.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountAccessPageViewModel @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getAccountHolderNameUseCase: GetAccountHolderNameUseCase,
    private val getAccountHolderUseCase: GetAccountHolderUseCase,
) : ViewModel() {
    private var _loadingState = MutableStateFlow<UiState<NameAndAccountType>>(UiState.Initial)
    val loadingState: StateFlow<UiState<NameAndAccountType>> = _loadingState

    fun saveToken(token: String) {
        viewModelScope.launch {
            _loadingState.value = UiState.Loading
            saveTokenUseCase(token)
            getNameAndAccountType()
        }
    }

    private suspend fun getNameAndAccountType() {
        val result = NameAndAccountType(
            name = getAccountHolderNameUseCase().fold(
                onSuccess = {
                    it.accountHolderName
                },
                onFailure = {
                    it as RepositoryException
                    _loadingState.value = UiState.Error(it.error.error, it.error.errorDescription)
                    ""
                },
            ),
            type = getAccountHolderUseCase().fold(
                onSuccess = {
                    it.type.displayName
                },
                onFailure = {
                    it as RepositoryException
                    _loadingState.value = UiState.Error(it.error.error, it.error.errorDescription)
                    ""
                },
            ),
        )

        if (_loadingState.value is UiState.Loading) {
            _loadingState.value = UiState.Success(result)
        }
    }
}