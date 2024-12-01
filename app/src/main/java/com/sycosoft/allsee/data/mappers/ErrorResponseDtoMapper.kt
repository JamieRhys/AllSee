package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import com.sycosoft.allsee.domain.models.ErrorResponse

object ErrorResponseDtoMapper {
    fun toDomain(dto: ErrorResponseDto): ErrorResponse = ErrorResponse(
        error = dto.error,
        errorDescription = dto.errorDescription
    )
}