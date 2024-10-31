package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.type.AccountHolderType
import java.util.UUID

/** Represent a Starling Bank account holder.
 *
 * @param uuid The unique identifier for the account holder provided by the bank.
 * @param type The type of account holder.
 *
 * @see AccountHolderType
 */
data class AccountHolder(
    val uuid: UUID,
    val type: AccountHolderType,
)

