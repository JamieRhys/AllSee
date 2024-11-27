package com.sycosoft.allsee.data.remote.models

import com.squareup.moshi.Json

/** Represents an error response from the API. */
data class ErrorResponseDto(
    @Json(name = "error") val error: String,
    @Json(name = "error_description") val errorDescription: String,
)