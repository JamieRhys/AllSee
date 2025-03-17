package uk.co.jaffakree.allsee.feature_login.mappers

import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.feature_login.models.NameAndAccountType

object NameAndAccountTypeMapper {
    fun map(person : Person) : NameAndAccountType =
        NameAndAccountType(
            name = "${person.firstName} ${person.lastName}",
            type = person.type.displayName
        )
}