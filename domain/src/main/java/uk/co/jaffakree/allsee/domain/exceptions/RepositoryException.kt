package uk.co.jaffakree.allsee.domain.exceptions

import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import java.io.IOException

/** Represents a repository exception used when an error occurs in the app repository */
class RepositoryException(val error: ErrorResponse): IOException()