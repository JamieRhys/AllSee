package com.sycosoft.allsee.data.local

import com.sycosoft.allsee.domain.models.ErrorResponse
import java.io.IOException

/** Exception thrown when database call fails
 *
 * @see ErrorResponse for what is passed to the caller
 * */
class DatabaseException(val errorResponse: ErrorResponse) : IOException()