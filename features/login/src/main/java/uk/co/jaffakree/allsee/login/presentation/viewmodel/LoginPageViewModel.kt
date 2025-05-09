package uk.co.jaffakree.allsee.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginPageViewModel @Inject constructor() : ViewModel() {
// region Variables
    data class ViewState(
        val accessToken: String,
        val showUserConfirmationDialog: Boolean,
        val name: String?,
        val accountType: String?,
    )

    private val _initialViewState = ViewState(
        accessToken = "",
        showUserConfirmationDialog = false,
        name = null,
        accountType = null,
    )

    private val _viewState = MutableStateFlow(_initialViewState)
    val viewState = _viewState.asStateFlow()

// endregion
// region Functions

    fun updateAccessToken(accessToken: String) = _viewState.update { it.copy(accessToken = accessToken) }

    private fun showUserConfirmationDialog() = _viewState.update {
        it.copy(
            showUserConfirmationDialog = !it.showUserConfirmationDialog
        )
    }

    private fun updateName(name: String?) = _viewState.update { it.copy(name = name) }

    private fun updateAccountType(accountType: String?) = _viewState.update { it.copy(accountType = accountType) }

// endregion
}