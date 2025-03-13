package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import uk.co.jaffakree.allsee.domain.models.ErrorResponse

object ErrorResponseMapper {
    fun toDomain(dto: ErrorResponseDto): ErrorResponse = ErrorResponse(
        error = dto.error,
        errorDescription = dto.errorDescription
    )
}