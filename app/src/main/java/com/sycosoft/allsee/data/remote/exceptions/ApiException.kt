package com.sycosoft.allsee.data.remote.exceptions

import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import java.io.IOException

/** Exception thrown when API call fails
 *
 * @see ErrorResponse for what is passed to the caller
 */
class ApiException(val errorResponse: ErrorResponseDto) : IOException()