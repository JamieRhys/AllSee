package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountAccessPageViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : ViewModel() {
    private var _response = MutableStateFlow("No response")
    val response: StateFlow<String> = _response

    fun saveToken(token: String) {
        viewModelScope.launch {
            appRepository.saveEncryptedToken(token)
            _response.value = appRepository.getAccountHolderName().accountHolderName
        }
    }
}