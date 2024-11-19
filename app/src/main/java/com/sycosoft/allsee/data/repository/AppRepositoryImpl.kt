package com.sycosoft.allsee.data.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.toDomain
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.domain.repository.AppResult
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val tokenProvider: TokenProvider,
) : AppRepository {
    override suspend fun saveToken(token: String) {
        tokenProvider.saveToken(token)
    }

    override suspend fun getAccountHolderName(): AppResult<AccountHolderName> {
        return try {
            AppResult.Success(apiService.getAccountHolderName().toDomain())
        } catch(e: ApiException) {
            if (e.errorResponse.errorDescription.contains("No access token provided in request")) {
                AppResult.Error(
                    ErrorResponse(
                        error = e.errorResponse.error,
                        errorDescription = e.errorResponse.errorDescription.substringBefore('.')
                    )
                )
            } else {
                AppResult.Error(e.errorResponse.toDomain())
            }
        } catch(e: Exception) {
            AppResult.Error(errorResponse = ErrorResponse(error = "unexpected_error", errorDescription = e.message!!))
        }
    }
}