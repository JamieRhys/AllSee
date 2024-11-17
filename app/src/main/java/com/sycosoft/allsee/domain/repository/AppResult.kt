package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.domain.models.ErrorResponse

/** Represents the result from the app repository */
sealed class AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>()
    data class Error(val errorResponse: ErrorResponse): AppResult<Nothing>()
}