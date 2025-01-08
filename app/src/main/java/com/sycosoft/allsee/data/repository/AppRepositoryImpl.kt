package com.sycosoft.allsee.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import com.sycosoft.allsee.data.local.DatabaseException
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.local.database.dao.AccountsDao
import com.sycosoft.allsee.data.local.database.dao.BalanceDao
import com.sycosoft.allsee.data.local.database.dao.PersonDao
import com.sycosoft.allsee.data.local.models.BalanceEntity
import com.sycosoft.allsee.data.local.models.PersonEntity
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.mappers.AccountHolderMapper
import com.sycosoft.allsee.domain.mappers.AccountsMapper
import com.sycosoft.allsee.domain.mappers.BalanceMapper
import com.sycosoft.allsee.domain.mappers.ErrorResponseMapper
import com.sycosoft.allsee.domain.mappers.FullBalanceMapper
import com.sycosoft.allsee.domain.mappers.IdentityMapper
import com.sycosoft.allsee.domain.mappers.PersonMapper
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.Balance
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.FullBalance
import com.sycosoft.allsee.domain.models.Identity
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.BalanceType
import com.sycosoft.allsee.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/** This is the implementation of the [AppRepository] interface. */
class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val personDao: PersonDao,
    private val accountsDao: AccountsDao,
    private val balanceDao: BalanceDao,
    private val tokenProvider: TokenProvider,
    private val balanceMapper: BalanceMapper,
    private val fullBalanceMapper: FullBalanceMapper,
    private val identityMapper: IdentityMapper,
    private val personMapper: PersonMapper,
) : AppRepository {
    private val logTag = AppRepositoryImpl::class.java.simpleName

    override suspend fun saveToken(token: String) {
        tokenProvider.saveToken(token)
    }

    override suspend fun savePerson(person: Person): Long = try {
        databaseCall { personDao.insertPerson(personMapper.toEntity(person)) }
    } catch(e: DatabaseException) {
        throw RepositoryException(e.errorResponse)
    }

    override suspend fun saveAccounts(accounts: List<Account>): List<Long> = try {
        databaseCall { accountsDao.insertAccounts(AccountsMapper.toEntity(accounts)) }
    } catch(e: DatabaseException) {
        throw RepositoryException(e.errorResponse)
    }

    override suspend fun saveFullBalance(fullBalance: FullBalance, accountUid: UUID): List<Long> = try {
        val list: List<Balance> = listOf(
            fullBalance.acceptedOverdraft,
            fullBalance.amount,
            fullBalance.clearedBalance,
            fullBalance.effectiveBalance,
            fullBalance.pendingTransactions,
            fullBalance.totalClearedBalance,
            fullBalance.totalEffectiveBalance
        )

        val savedList: List<Long> = emptyList()

        list.forEach {
            savedList.plus(databaseCall{balanceDao.insertBalance(listOf(balanceMapper.toEntity(it, accountUid)))})
        }

        savedList
    } catch(e: DatabaseException) {
        throw RepositoryException(e.errorResponse)
    }

    override suspend fun getAccounts(): List<Account> = try {
        coroutineScope {
            var accounts: List<Account> = AccountsMapper.toDomain(databaseCall { accountsDao.getAccounts() } )

            if (accounts.isEmpty()) {
                val accountList = mutableListOf<Account>()
                val accountsDto = async { apiService.getAccounts() }.await()

                accountsDto.accounts.forEach { accountDto ->
                    val identifier = async { apiService.getAccountIdentifiers(accountDto.accountUid) }.await()

                    val account = AccountsMapper.toDomain(
                        dto = accountDto,
                        identifier = identifier,
                    )

                    accountList.add(account)
                }

                accounts = accountList
                saveAccounts(accounts)
            }

            accounts
        }

    } catch (e: ApiException) {
        throw throwRepositoryException(e)
    } catch (e: DatabaseException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getAccountHolder(): AccountHolder = try {
        AccountHolderMapper.toDomain(apiService.getAccountHolder())
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getIdentity(): Identity = try {
        identityMapper.toDomain(apiService.getIdentity())
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getFullBalance(): FullBalance = try {
        // TODO: Add index of the account the full balance is needed for.
        coroutineScope {
            val accounts = getAccounts()
            var fullBalance: FullBalance? = null

            val acceptedOverdraft: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.ACCEPTED_OVERDRAFT.ordinal,
            )
            val amount: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.AMOUNT.ordinal,
            )
            val clearedBalance: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.CLEARED_BALANCE.ordinal,
            )
            val effectiveBalance: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.EFFECTIVE_BALANCE.ordinal,
            )
            val totalClearedBalance: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.TOTAL_CLEARED_BALANCE.ordinal,
            )
            val totalEffectiveBalance: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.TOTAL_EFFECTIVE_BALANCE.ordinal,
            )
            val pendingTransactions: BalanceEntity? = balanceDao.getBalanceFromType(
                accountUid = accounts[0].accountUid.toString(),
                type = BalanceType.PENDING_TRANSACTIONS.ordinal,
            )

            if (acceptedOverdraft != null &&
                amount != null &&
                clearedBalance != null &&
                effectiveBalance != null &&
                totalClearedBalance != null &&
                totalEffectiveBalance != null &&
                pendingTransactions != null) {
                fullBalance = FullBalance(
                    acceptedOverdraft = balanceMapper.toDomain(acceptedOverdraft),
                    pendingTransactions = balanceMapper.toDomain(pendingTransactions),
                    amount = balanceMapper.toDomain(amount),
                    clearedBalance = balanceMapper.toDomain(clearedBalance),
                    effectiveBalance = balanceMapper.toDomain(effectiveBalance),
                    totalClearedBalance = balanceMapper.toDomain(totalClearedBalance),
                    totalEffectiveBalance = balanceMapper.toDomain(totalEffectiveBalance),
                )
            }

            if (fullBalance == null) {
                fullBalance = fullBalanceMapper.toDomain(apiService.getFullBalance(accounts[0].accountUid.toString()))

                saveFullBalance(fullBalance, accounts[0].accountUid)

            }
            fullBalance
        }
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    } catch(e: DatabaseException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getBalance(type: BalanceType): Balance = try {
        val fullBalance = getFullBalance()

        when (type) {
            BalanceType.ACCEPTED_OVERDRAFT -> fullBalance.acceptedOverdraft
            BalanceType.AMOUNT -> fullBalance.amount
            BalanceType.CLEARED_BALANCE -> fullBalance.clearedBalance
            BalanceType.EFFECTIVE_BALANCE -> fullBalance.effectiveBalance
            BalanceType.PENDING_TRANSACTIONS -> fullBalance.pendingTransactions
            BalanceType.TOTAL_CLEARED_BALANCE -> fullBalance.totalClearedBalance
            BalanceType.TOTAL_EFFECTIVE_BALANCE -> fullBalance.totalEffectiveBalance
        }
    } catch(e: RepositoryException) {
        throw e
    }

    @Throws(RepositoryException::class)
    override suspend fun getPerson(): Person = try {
        coroutineScope {
            var person = personDao.getPerson()

            if (person == null) {
                val accountHolder = async { getAccountHolder() }.await()
                val identity = async { getIdentity() }.await()

                person = PersonEntity(
                    uid = accountHolder.uid,
                    type = accountHolder.type.toString(),
                    title = identity.title,
                    firstName = identity.firstName,
                    lastName = identity.lastName,
                    dob = identity.dob.toString(),
                    email = identity.email,
                    phone = identity.phone,
                )

                savePerson(personMapper.toDomain(person))
            }

            personMapper.toDomain(person)
        }
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    } catch(e: DatabaseException) {
        throw throwRepositoryException(e)
    }

    private fun throwRepositoryException(e: ApiException): RepositoryException {
        Log.e(logTag, "error = ${e.errorResponse.error}, errorDescription = ${e.errorResponse.errorDescription}")

        return if (e.errorResponse.errorDescription.contains("No access token provided in request")) {
            RepositoryException(
                error = ErrorResponse(
                    error = e.errorResponse.error,
                    errorDescription = e.errorResponse.errorDescription.substringBefore('.')
                )
            )
        } else {
            RepositoryException(error = ErrorResponseMapper.toDomain(e.errorResponse))
        }
    }

    private fun throwRepositoryException(e: DatabaseException): RepositoryException {
        Log.e(logTag, "error = ${e.errorResponse.error}, errorDescription = ${e.errorResponse.errorDescription}")

        return RepositoryException(error = e.errorResponse)
    }

    @Throws(DatabaseException::class)
    private suspend fun <T> databaseCall(operation: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            try {
                operation()
            } catch(e: SQLiteException) {
                coroutineContext.ensureActive()
                Log.e(logTag, "Database Error: {SQLiteException}: ${e.message}")

                throw DatabaseException(ErrorResponse("database_error", "{SQLiteException}: ${e.message}"))
            } catch(e: IllegalStateException) {
                coroutineContext.ensureActive()
                Log.e(logTag, "Database Error: {IllegalStateException}: ${e.message}")

                throw DatabaseException(ErrorResponse("database_error", e.message.toString()))
            } catch(e: Exception) {
                coroutineContext.ensureActive()
                Log.e(logTag, "Database Error: {Unknown}: ${e.message}")

                throw DatabaseException(ErrorResponse("database_error", "Unknown Database Error: ${e.message.toString()}"))
            }
        }
    }
}