package com.sycosoft.allsee.data.utils

import com.sycosoft.allsee.data.remote.models.ErrorResponseDto

/** Represents the response results of an API call */
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: ErrorResponseDto) : ApiResponse<Nothing>()
}