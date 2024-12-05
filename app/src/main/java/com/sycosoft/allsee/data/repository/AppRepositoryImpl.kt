package com.sycosoft.allsee.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import com.sycosoft.allsee.data.local.DatabaseException
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.local.database.dao.PersonDao
import com.sycosoft.allsee.data.local.models.PersonEntity
import com.sycosoft.allsee.domain.mappers.AccountHolderMapper
import com.sycosoft.allsee.domain.mappers.ErrorResponseMapper
import com.sycosoft.allsee.domain.mappers.IdentityMapper
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.mappers.PersonMapper
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.Identity
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val personDao: PersonDao,
    private val tokenProvider: TokenProvider,
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