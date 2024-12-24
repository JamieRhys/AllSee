package com.sycosoft.allsee.domain.exceptions

import com.sycosoft.allsee.domain.models.ErrorResponse
import java.io.IOException

/** Represents a repository exception used when an error occurs in the app repository */
class RepositoryException(val error: ErrorResponse): IOException()