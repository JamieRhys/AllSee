package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.models.NameAndAccountType

interface AppRepository {
    /** Encrypts the given token and then saves the result into the [TokenProvider] */
    suspend fun saveToken(token: String)

    /** Retrieves the account holder name*/
    suspend fun getAccountHolderName(): Result<AccountHolderName>

    /** Retrieves the account holder */
    suspend fun getAccountHolder(): Result<AccountHolder>

    suspend fun getNameAndAccountType(): Result<NameAndAccountType>
}