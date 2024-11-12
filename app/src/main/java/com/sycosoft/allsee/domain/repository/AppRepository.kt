package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.local.TokenProvider

interface AppRepository {
    /** Encrypts the given token and then saves the result into the [TokenProvider] */
    suspend fun saveEncryptedToken(token: String)

    /** Retrieves the account holder name from the API */
    suspend fun getAccountHolderName(): AccountHolderNameDto
}