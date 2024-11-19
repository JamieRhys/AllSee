package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.domain.models.AccountHolderName

interface AppRepository {
    /** Encrypts the given token and then saves the result into the [TokenProvider] */
    suspend fun saveToken(token: String)

    /** Retrieves the account holder name from the API */
    suspend fun getAccountHolderName(): Result<AccountHolderName>
}