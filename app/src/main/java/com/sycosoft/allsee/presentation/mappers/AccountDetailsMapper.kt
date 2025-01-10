package com.sycosoft.allsee.presentation.mappers

import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.presentation.models.AccountDetails

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