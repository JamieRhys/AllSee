package com.sycosoft.allsee.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.co.jaffakree.allsee.domain.usecases.GetPersonUseCase
import uk.co.jaffakree.allsee.domain.usecases.SaveTokenUseCase
import com.sycosoft.allsee.presentation.mappers.NameAndAccountTypeMapper
import com.sycosoft.allsee.presentation.models.NameAndAccountType
import uk.co.jaffakree.allsee.core.ui.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import javax.inject.Inject

class AccountAccessPageViewModel @Inject constructor(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {

    private val nameAndAccountTypeUiState = MutableStateFlow<UiState<NameAndAccountType>>(value = UiState.Initial)
    private val accessToken = MutableStateFlow(value = "")

    val viewState: StateFlow<ViewState> by lazy {
        combine(
            accessToken,
            nameAndAccountTypeUiState,
            ::ViewState
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ViewState(
                accessToken = accessToken.value,
                nameAndAccountState = nameAndAccountTypeUiState.value
            )
        )
    }

    fun saveToken() {
        viewModelScope.launch {
            nameAndAccountTypeUiState.value = UiState.Loading
            saveTokenUseCase(token = accessToken.value)
            nameAndAccountTypeUiState.value = getPersonState()
        }
    }

    fun updateAccessToken(token: String) {
        accessToken.update { token }
    }

    fun resetLoadingState() {
        nameAndAccountTypeUiState.update { UiState.Initial }
    }

    private suspend fun getPersonState() = try {
        UiState.Success(NameAndAccountTypeMapper.map(getPersonUseCase()))
    } catch (e: RepositoryException) {
        UiState.Error(e.error.error, e.error.errorDescription)
    }

    data class ViewState(
        val accessToken: String,
        val nameAndAccountState: UiState<NameAndAccountType>
    )
}