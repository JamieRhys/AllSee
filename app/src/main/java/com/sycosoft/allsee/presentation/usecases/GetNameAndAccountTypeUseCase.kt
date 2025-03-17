package com.sycosoft.allsee.presentation.usecases

import uk.co.jaffakree.allsee.feature_login.models.NameAndAccountType
import uk.co.jaffakree.allsee.domain.models.Person

class GetNameAndAccountTypeUseCase {
    operator fun invoke(person: Person): NameAndAccountType = NameAndAccountType(name = person.firstName + " " + person.lastName, type = person.type.displayName)
}