package com.sycosoft.allsee.domain.util

sealed class AppResult<out T> {
    data class Success<T>(val data: T): AppResult<T>()
    data class Failure(val message: String? = null, val code: ResultCode): AppResult<Nothing>()
}