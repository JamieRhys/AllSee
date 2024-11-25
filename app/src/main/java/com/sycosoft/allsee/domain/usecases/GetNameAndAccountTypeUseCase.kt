package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.models.Person

class GetNameAndAccountTypeUseCase {
    operator fun invoke(person: Person): NameAndAccountType = NameAndAccountType(name = person.firstName + " " + person.lastName, type = person.type.displayName)
}