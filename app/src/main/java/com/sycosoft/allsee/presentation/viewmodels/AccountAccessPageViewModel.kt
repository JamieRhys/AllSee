package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.usecases.GetPersonUseCase
import com.sycosoft.allsee.domain.usecases.SaveTokenUseCase
import com.sycosoft.allsee.presentation.mappers.NameAndAccountTypeMapper
import com.sycosoft.allsee.presentation.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AccountAccessPageViewModel @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {
    private var _loadingState = MutableStateFlow<UiState<NameAndAccountType>>(UiState.Initial)
    val loadingState: StateFlow<UiState<NameAndAccountType>> = _loadingState

    private var _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken

    fun saveToken() {
        _loadingState.value = UiState.Loading
        viewModelScope.launch {
            saveTokenUseCase(_accessToken.value)
            getPerson()
        }
    }

    fun updateAccessToken(token: String) { _accessToken.value = token }

    fun resetLoadingState() { _loadingState.value = UiState.Initial }

    private suspend fun getPerson() = try {
        _loadingState.value = UiState.Success(NameAndAccountTypeMapper.map(getPersonUseCase()))
    } catch(e: RepositoryException) {
        _loadingState.value = UiState.Error(e.error.error, e.error.errorDescription)
    }
}