package uk.co.jaffakree.allsee.remote.exceptions

import uk.co.jaffakree.allsee.remote.models.ErrorResponseDto
import java.io.IOException

/** Exception thrown when API call fails
 *
 * @see ErrorResponse for what is passed to the caller
 */
class ApiException(val errorResponse: ErrorResponseDto) : IOException()