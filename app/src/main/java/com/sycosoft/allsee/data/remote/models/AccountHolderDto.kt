package com.sycosoft.allsee.data.remote.models

import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.types.AccountHolderType

/** Represents and account holder provided by the Starling Bank API.
 *
 * @property accountHolderUid The unique identifier for the account holder that's provided by Starling Bank.
 * @property accountHolderType The type of account the holder holds.
 */
data class AccountHolderDto(
    val accountHolderUid: String,
    val accountHolderType: String,
)

/** Maps an [AccountHolderDto] class to its corresponding Domain [AccountHolder] class */
fun AccountHolderDto.toDomain(): AccountHolder = AccountHolder(
    uid = accountHolderUid,
    type = AccountHolderType.valueOf(accountHolderType)
)