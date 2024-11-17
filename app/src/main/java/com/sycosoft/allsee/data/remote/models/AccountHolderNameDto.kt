package com.sycosoft.allsee.data.remote.models

import com.sycosoft.allsee.domain.models.AccountHolderName

/** Represents the name of the account holder.
 *
 * @property accountHolderName The name of the account holder.
 */
data class AccountHolderNameDto(
    val accountHolderName: String,
)

/** Maps an [AccountHolderNameDto] class to its corresponding Domain [AccountHolderName] class. */
fun AccountHolderNameDto.toDomain(): AccountHolderName = AccountHolderName(
    accountHolderName = accountHolderName,
)

// TODO: Add toDatabase mapper.