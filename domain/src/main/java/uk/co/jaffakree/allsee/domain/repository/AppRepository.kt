package uk.co.jaffakree.allsee.domain.repository

import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.AccountHolder
import uk.co.jaffakree.allsee.domain.models.Balance
import uk.co.jaffakree.allsee.domain.models.FullBalance
import uk.co.jaffakree.allsee.domain.models.Identity
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import java.util.UUID

interface AppRepository {
    /** Encrypts the given token and then saves the result into the TokenProvider */
    suspend fun saveToken(token: String)

    /** Saves [Person] to the database so that it can be retrieved later */
    suspend fun savePerson(person: Person): Long

    /** Saves all user [Account]s to the database */
    suspend fun saveAccounts(accounts: List<Account>): List<Long>

    suspend fun saveFullBalance(fullBalance: FullBalance, accountUid: UUID): List<Long>

    /** Retrieves all the [Account]s from the database */

    suspend fun getAccounts(): List<Account>

    /** Retrieves the [AccountHolder] */
    @Throws(RepositoryException::class)
    suspend fun getAccountHolder(): AccountHolder

    @Throws(RepositoryException::class)
    suspend fun getFullBalance(): FullBalance

    @Throws(RepositoryException::class)
    suspend fun getBalance(type: BalanceType): Balance

    @Throws(RepositoryException::class)
    suspend fun getIdentity(): Identity

    @Throws(RepositoryException::class)
    suspend fun getPerson(): Person
}