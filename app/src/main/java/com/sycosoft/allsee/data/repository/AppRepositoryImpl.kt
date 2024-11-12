package com.sycosoft.allsee.data.repository

import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: StarlingBankApiService,
    private val tokenProvider: TokenProvider,
    private val cryptoManager: CryptoManager,
) : AppRepository {
    override suspend fun saveEncryptedToken(token: String) {
        val encryptedToken = cryptoManager.encrypt(token.toByteArray())

        if (encryptedToken.isNotEmpty()) {
            tokenProvider.saveEncryptedToken(encryptedToken)
            println("TOKEN SAVED SUCCESSFULLY!")
        } else {
            println("ERROR!: Token is empty!")
        }
    }

    override suspend fun getAccountHolderName(): AccountHolderNameDto = apiService.getAccountHolderName()
}