package com.sycosoft.allsee.data.remote.models

/** Represents a list of accounts a holder currently has to their name.
 *
 * @property accounts The list of currently held accounts.
 */
data class AccountListDto(
    val accounts: List<AccountDto>
)
