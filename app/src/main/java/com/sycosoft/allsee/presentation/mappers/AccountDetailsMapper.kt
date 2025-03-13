package com.sycosoft.allsee.presentation.mappers

import com.sycosoft.allsee.presentation.models.AccountDetails
import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.Person

object AccountDetailsMapper {
    fun map(account: Account, person: Person): AccountDetails =
        AccountDetails(
            name = "${person.firstName} ${person.lastName}",
            type = person.type.displayName,
            accountNumber = account.accountIdentifier,
            sortCode = account.bankIdentifier,
            iban = account.iban,
            bic = account.bic,
        )
}