package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.local.models.PersonEntity
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import java.time.LocalDate
import javax.inject.Inject

class PersonMapper @Inject constructor() {
    fun toDomain(entity: PersonEntity): Person = Person(
        id = entity.id,
        uid = entity.uid,
        type = AccountHolderType.valueOf(entity.type),
        title = entity.title,
        firstName = entity.firstName,
        lastName = entity.lastName,
        dob = LocalDate.parse(entity.dob),
        email = entity.email,
        phone = entity.phone,
    )
}