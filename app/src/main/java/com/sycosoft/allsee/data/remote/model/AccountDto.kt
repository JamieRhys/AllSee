package com.sycosoft.allsee.data.remote.model

/** Represents an Account provided by the Starling Bank API.
 *
 * @param accountUid The unique identifier for the account that's provided by Starling Bank.
 * @param accountType The type of account.
 * @param defaultCategory The UUID of the default category for the account.
 * @param currency The currency used by the account.
 * @param createdAt The date and time of when the account was created.
 * @param name The name of the account.
 */
data class AccountDto(
    val accountUid: String,
    val accountType: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: String,
    val name: String,
)
