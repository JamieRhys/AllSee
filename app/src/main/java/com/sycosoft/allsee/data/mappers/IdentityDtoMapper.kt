package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.models.IdentityDto
import com.sycosoft.allsee.domain.models.Identity
import java.time.LocalDate

object IdentityDtoMapper {
    fun toDomain(dto: IdentityDto): Identity = Identity(
        title = dto.title,
        firstName = dto.firstName,
        lastName = dto.lastName,
        dob = LocalDate.parse(dto.dateOfBirth),
        email = dto.email,
        phone = dto.phone,
    )
}