package com.sycosoft.allsee.data.remote.models

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
    val accountUid: String,
    val accountType: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: String,
    val name: String,
)