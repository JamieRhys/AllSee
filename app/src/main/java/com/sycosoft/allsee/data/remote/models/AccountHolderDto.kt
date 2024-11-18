package com.sycosoft.allsee.data.remote.models

/** Represents and account holder provided by the Starling Bank API.
 *
 * @property accountHolderUid The unique identifier for the account holder that's provided by Starling Bank.
 * @property accountHolderType The type of account the holder holds.
 */
data class AccountHolderDto(
    val accountHolderUid: String,
    val accountHolderType: String,
)
