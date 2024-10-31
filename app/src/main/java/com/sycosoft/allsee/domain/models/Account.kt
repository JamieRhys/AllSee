package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.type.AccountType
import com.sycosoft.allsee.domain.models.type.CurrencyType
import java.time.LocalDateTime
import java.util.UUID

/** Represents a Starling Bank Account.
 *
 * @param uuid The unique identifier for the account that's provided by Starling Bank.
 * @param type The type of account.
 * @param defaultCategory The unique identifier for the default category for the account.
 * @param currency The default currency used by the account.
 * @param createdAt The date and time of when the account was created.
 * @param name The name of the account provided by the owner of the account.
 *
 * @see AccountType
 * @see CurrencyType
 */
data class Account(
    val uuid: UUID,
    val type: AccountType,
    val defaultCategory: UUID,
    val currency: CurrencyType,
    val createdAt: LocalDateTime,
    val name: String,
)

