package com.sycosoft.allsee.data.remote.exceptions

import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import java.io.IOException

class ApiException(val errorResponse: ErrorResponseDto) : IOException()