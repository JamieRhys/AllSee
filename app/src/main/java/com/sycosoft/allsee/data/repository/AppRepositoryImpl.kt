package com.sycosoft.allsee.data.repository

import android.util.Log
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.toDomain
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.repository.AppRepository
import kotlinx.coroutines.ensureActive
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val tokenProvider: TokenProvider,
) : AppRepository {
    private val logTag = AppRepositoryImpl::class.java.simpleName

    override suspend fun saveToken(token: String) {
        tokenProvider.saveToken(token)
    }

    @Throws(RepositoryException::class)
    override suspend fun getAccountHolderName(): AccountHolderName = try {
        apiService.getAccountHolderName().toDomain()
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getAccountHolder(): AccountHolder = try {
        apiService.getAccountHolder().toDomain()
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    @Throws(RepositoryException::class)
    override suspend fun getNameAndAccountType(): NameAndAccountType = try {
        NameAndAccountType(
            name = getAccountHolderName().accountHolderName,
            type = getAccountHolder().type.displayName,
        )
    } catch(e: ApiException) {
        throw throwRepositoryException(e)
    }

    private suspend fun throwRepositoryException(e: ApiException): ApiException {
        coroutineContext.ensureActive()
        Log.e(logTag, "error = ${e.errorResponse.error}, errorDescription = ${e.errorResponse.errorDescription}")

        if (e.errorResponse.errorDescription.contains("No access token provided in request")) {
            throw RepositoryException(
                error = ErrorResponse(
                    error = e.errorResponse.error,
                    errorDescription = e.errorResponse.errorDescription.substringBefore('.')
                )
            )
        } else {
            throw RepositoryException(error = e.errorResponse.toDomain())
        }
    }
}