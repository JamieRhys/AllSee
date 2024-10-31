package com.sycosoft.allsee.domain.models

/** Used when the Starling Bank API returns an error.
 *
 * @param code The HTTP Code status.
 * @param error: The error type.
 * @param errorDescription A description of what went wrong for the API.
 */
data class ErrorResponse(
    val code: Int,
    val error: String,
    val errorDescription: String
)
