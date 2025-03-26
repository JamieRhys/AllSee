package uk.co.jaffakree.allsee.remote.models

import uk.co.jaffakree.allsee.domain.models.types.AccountType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import java.time.OffsetDateTime
import java.util.UUID

/** Represents an Account provided by the Starling Bank API
 *
 * @property accountUid The unique identifier for the account that's provided by Starling Bank.
 * @property accountType The type of account.
 * @property defaultCategory The UUID of the default category for the account.
 * @property currency The currency of the account.
 * @property createdAt The date and time of when the account was created.
 * @property name The name of the account.
 */
data class AccountDto(
    val accountUid: UUID,
    val accountType: AccountType,
    val defaultCategory: UUID,
    val currency: CurrencyType,
    val createdAt: OffsetDateTime,
    val name: String,
)