package uk.co.jaffakree.allsee.core.ui.utils

sealed class UiState<out T> {
    data object Initial : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val error: String, val errorDescription: String) : UiState<Nothing>()
}