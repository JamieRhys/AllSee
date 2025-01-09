package com.sycosoft.allsee.presentation.mappers

import com.sycosoft.allsee.presentation.models.NameAndAccountType
import com.sycosoft.allsee.domain.models.Person

object NameAndAccountTypeMapper {
    fun map(person : Person) : NameAndAccountType =
        NameAndAccountType(
            name = "${person.firstName} ${person.lastName}",
            type = person.type.displayName
        )
}