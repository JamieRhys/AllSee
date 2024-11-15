package com.sycosoft.allsee.data.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val tokenProvider: TokenProvider,
) : AppRepository {
    override suspend fun saveToken(token: String) {
        tokenProvider.saveToken(token)
    }

    override suspend fun getAccountHolderName(): AccountHolderNameDto =
        apiService.getAccountHolderName()
}