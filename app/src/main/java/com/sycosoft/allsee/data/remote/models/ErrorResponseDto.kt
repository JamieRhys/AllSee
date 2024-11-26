package com.sycosoft.allsee.data.remote.models

import com.squareup.moshi.Json
import com.sycosoft.allsee.domain.models.ErrorResponse

/** Represents an error response from the API. */
data class ErrorResponseDto(
    @Json(name = "error") val error: String,
    @Json(name = "error_description") val errorDescription: String,
)

/** Maps an [ErrorResponseDto] class to its corresponding Domain [ErrorResponse] class. */
fun ErrorResponseDto.toDomain(): ErrorResponse = ErrorResponse(
    error = error,
    errorDescription = errorDescription,
)