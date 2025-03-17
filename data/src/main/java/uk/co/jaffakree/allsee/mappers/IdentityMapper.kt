package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.domain.models.Identity
import uk.co.jaffakree.allsee.remote.models.IdentityDto
import java.time.LocalDate
import javax.inject.Inject

class IdentityMapper @Inject constructor() {
    fun toDomain(dto: IdentityDto): Identity = Identity(
        title = dto.title,
        firstName = dto.firstName,
        lastName = dto.lastName,
        dob = LocalDate.parse(dto.dateOfBirth),
        email = dto.email,
        phone = dto.phone,
    )
}