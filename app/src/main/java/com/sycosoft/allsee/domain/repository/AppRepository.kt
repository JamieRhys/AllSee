package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.models.Identity
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.models.Person

interface AppRepository {
    /** Encrypts the given token and then saves the result into the [TokenProvider] */
    suspend fun saveToken(token: String)

    /** Retrieves the account holder name*/
    @Throws(RepositoryException::class)
    suspend fun getAccountHolderName(): AccountHolderName

    /** Retrieves the account holder */
    @Throws(RepositoryException::class)
    suspend fun getAccountHolder(): AccountHolder

    /** Provides the name and account type of the current account holder */
    @Throws(RepositoryException::class)
    suspend fun getNameAndAccountType(): NameAndAccountType

    @Throws(RepositoryException::class)
    suspend fun getIdentity(): Identity

    @Throws
    suspend fun getPerson(): Person
}