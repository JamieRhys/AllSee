package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import uk.co.jaffakree.allsee.remote.models.ErrorResponseDto

object ErrorResponseMapper {
    fun toDomain(dto: ErrorResponseDto): ErrorResponse = ErrorResponse(
        error = dto.error,
        errorDescription = dto.errorDescription
    )
}