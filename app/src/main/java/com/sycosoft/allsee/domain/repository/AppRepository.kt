package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.Identity
import com.sycosoft.allsee.domain.models.Person

interface AppRepository {
    /** Encrypts the given token and then saves the result into the [TokenProvider] */
    suspend fun saveToken(token: String)

    /** Saves Person to the database so that it can be retrieved later */
    suspend fun savePerson(person: Person): Long

    /** Retrieves the account holder */
    @Throws(RepositoryException::class)
    suspend fun getAccountHolder(): AccountHolder

    @Throws(RepositoryException::class)
    suspend fun getIdentity(): Identity

    @Throws
    suspend fun getPerson(): Person
}