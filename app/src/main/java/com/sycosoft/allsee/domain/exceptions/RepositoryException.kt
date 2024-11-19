package com.sycosoft.allsee.domain.exceptions

import com.sycosoft.allsee.domain.models.ErrorResponse
import java.io.IOException

class RepositoryException(val error: ErrorResponse): IOException()