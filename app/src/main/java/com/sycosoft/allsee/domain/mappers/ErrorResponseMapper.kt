package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import com.sycosoft.allsee.domain.models.ErrorResponse

object ErrorResponseMapper {
    fun toDomain(dto: ErrorResponseDto): ErrorResponse = ErrorResponse(
        error = dto.error,
        errorDescription = dto.errorDescription
    )
}