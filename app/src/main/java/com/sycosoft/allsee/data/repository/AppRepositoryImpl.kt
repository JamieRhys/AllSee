package com.sycosoft.allsee.data.repository

import android.util.Log
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.mappers.AccountHolderDtoMapper
import com.sycosoft.allsee.data.mappers.ErrorResponseDtoMapper
import com.sycosoft.allsee.data.mappers.IdentityDtoMapper
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.Identity
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.repository.AppRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val tokenProvider: TokenProvider,
    private val identityDtoMapper: IdentityDtoMapper,
) : AppRepository {
    private val logTag = AppRepositoryImpl::class.java.simpleName

    override suspend fun saveToken(token: String) {
        tokenProvider.saveToken(token)
    }

    @Throws(RepositoryException::class)
    override suspend fun getAccountHolder(): AccountHolder = try {
        AccountHolderDtoMapper.toDomain(apiService.getAccountHolder())
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getIdentity(): Identity = try {
        identityDtoMapper.toDomain(apiService.getIdentity())
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getPerson(): Person = try {
        coroutineScope {
            val accountHolder = async { getAccountHolder() }.await()
            val identity = async { getIdentity() }.await()

            Person(
                uid = accountHolder.uid,
                type = accountHolder.type,
                title = identity.title,
                firstName = identity.firstName,
                lastName = identity.lastName,
                dob = identity.dob,
                email = identity.email,
                phone = identity.phone,
            )
        }
    } catch(e: ApiException) {
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
            RepositoryException(error = ErrorResponseDtoMapper.toDomain(e.errorResponse))
        }
    }
}