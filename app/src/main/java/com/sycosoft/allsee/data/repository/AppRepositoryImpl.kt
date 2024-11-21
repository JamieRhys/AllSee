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

    override suspend fun getAccountHolderName(): Result<AccountHolderName> {
        return try {
            Result.success(apiService.getAccountHolderName().toDomain())
        } catch(e: ApiException) {
            Log.e(logTag, "error = ${e.errorResponse.error}, errorDescription = ${e.errorResponse.errorDescription}")
            if (e.errorResponse.errorDescription.contains("No access token provided in request")) {
                Result.failure(
                    RepositoryException(
                        error = ErrorResponse(
                            error = e.errorResponse.error,
                            errorDescription = e.errorResponse.errorDescription.substringBefore('.')
                        )
                    )
                )
            } else {
                Result.failure(RepositoryException(error = e.errorResponse.toDomain()))
            }
        } catch(e: Throwable) {
            coroutineContext.ensureActive()
            Log.e(logTag, "error = unknown_error, errorDescription = Unexpected error while handling request")
            Result.failure(
                RepositoryException(
                    error = ErrorResponse(
                        error = "unknown_error",
                        errorDescription = "Unexpected error while handling request"
                    )
                )
            )
        }
    }

    override suspend fun getAccountHolder(): Result<AccountHolder> {
        return try {
            Result.success(apiService.getAccountHolder().toDomain())
        } catch(e: ApiException) {
            Log.e(logTag, "error = ${e.errorResponse.error}, errorDescription = ${e.errorResponse.errorDescription}")
            if (e.errorResponse.errorDescription.contains("No access token provided in request")) {
                Result.failure(
                    RepositoryException(
                        error = ErrorResponse(
                            error = e.errorResponse.error,
                            errorDescription = e.errorResponse.errorDescription.substringBefore('.')
                        )
                    )
                )
            } else {
                Result.failure(RepositoryException(error = e.errorResponse.toDomain()))
            }
        } catch(e: Throwable) {
            coroutineContext.ensureActive()
            Log.e(logTag, "error = unknown_error, errorDescription = Unexpected error while handling request")
            Result.failure(
                RepositoryException(
                    error = ErrorResponse(
                        error = "unknown_error",
                        errorDescription = "Unexpected error while handling request"
                    )
                )
            )
        }
    }

    override suspend fun getNameAndAccountType(): Result<NameAndAccountType> {
        return try {
            val name = getAccountHolderName().getOrThrow().accountHolderName
            val accountHolder = getAccountHolder().getOrThrow()

            Result.success(
                NameAndAccountType(
                    name = name,
                    type = accountHolder.type.displayName,
                )
            )
        } catch(e: ApiException) {
            Log.e(logTag, "error = ${e.errorResponse.error}, errorDescription = ${e.errorResponse.errorDescription}")
            if (e.errorResponse.errorDescription.contains("No access token provided in request")) {
                Result.failure(
                    RepositoryException(
                        error = ErrorResponse(
                            error = e.errorResponse.error,
                            errorDescription = e.errorResponse.errorDescription.substringBefore('.')
                        )
                    )
                )
            } else {
                Result.failure(RepositoryException(error = e.errorResponse.toDomain()))
            }
        } catch(e: Throwable) {
            coroutineContext.ensureActive()
            Log.e(logTag, "error = unknown_error, errorDescription = Unexpected error while handling request")
            Result.failure(
                RepositoryException(
                    error = ErrorResponse(
                        error = "unknown_error",
                        errorDescription = "Unexpected error while handling request"
                    )
                )
            )
        }
    }
}