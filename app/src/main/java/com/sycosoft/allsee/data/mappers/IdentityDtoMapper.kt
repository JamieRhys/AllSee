package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.models.IdentityDto
import com.sycosoft.allsee.domain.models.Identity
import java.time.LocalDate
import javax.inject.Inject

class IdentityDtoMapper @Inject constructor() {
    fun toDomain(dto: IdentityDto): Identity = Identity(
        title = dto.title,
        firstName = dto.firstName,
        lastName = dto.lastName,
        dob = LocalDate.parse(dto.dateOfBirth),
        email = dto.email,
        phone = dto.phone,
    )
}