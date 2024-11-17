package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.domain.repository.AppResult
import com.sycosoft.allsee.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountAccessPageViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : ViewModel() {
    private var _response = MutableStateFlow("No response")
    val response: StateFlow<String> = _response

    private var _loadingState = MutableStateFlow<UiState<String>>(UiState.Initial)
    val loadingState: StateFlow<UiState<String>> = _loadingState

    fun saveToken(token: String) {
        viewModelScope.launch {
            _loadingState.value = UiState.Loading
            appRepository.saveToken(token)

            _loadingState.value = when(val accountHolderName = appRepository.getAccountHolderName()) {
                is AppResult.Error -> UiState.Error(error = accountHolderName.errorResponse.error, errorDescription = accountHolderName.errorResponse.errorDescription)
                is AppResult.Success -> UiState.Success(data = accountHolderName.data.accountHolderName)
            }
        }
    }
}