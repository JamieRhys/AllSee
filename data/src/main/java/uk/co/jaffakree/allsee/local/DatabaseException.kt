package uk.co.jaffakree.allsee.data.local

import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import java.io.IOException

/** Exception thrown when database call fails
 *
 * @see ErrorResponse for what is passed to the caller
 */
class DatabaseException(val errorResponse: ErrorResponse) : IOException()