package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.data.local.models.PersonEntity
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class PersonMapper @Inject constructor() {
    fun toDomain(entity: PersonEntity): Person = Person(
        uid = UUID.fromString(entity.uid),
        type = AccountHolderType.valueOf(entity.type),
        title = entity.title,
        firstName = entity.firstName,
        lastName = entity.lastName,
        dob = LocalDate.parse(entity.dob),
        email = entity.email,
        phone = entity.phone,
    )

    fun toEntity(domain: Person): PersonEntity = PersonEntity(
        uid = domain.uid.toString(),
        type = domain.type.name,
        title = domain.title,
        firstName = domain.firstName,
        lastName = domain.lastName,
        dob = domain.dob.toString(),
        email = domain.email,
        phone = domain.phone,
    )
}