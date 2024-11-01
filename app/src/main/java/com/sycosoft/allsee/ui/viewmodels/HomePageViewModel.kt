package com.sycosoft.allsee.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.data.repository.AppRepositoryImpl
import com.sycosoft.allsee.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val appRepository: AppRepository = AppRepositoryImpl()
): ViewModel() {
    private val _account: MutableStateFlow<String> = MutableStateFlow("No data")
    val account: StateFlow<String> = _account

    init {
        viewModelScope.launch {
            _account.value = appRepository.getAccount().toString()
        }
    }

    private suspend fun getAccount() {
        _account.value = appRepository.getAccount().toString()
    }

}