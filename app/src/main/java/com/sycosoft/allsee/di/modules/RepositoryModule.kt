package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.data.repository.AppRepositoryImpl
import com.sycosoft.allsee.domain.repository.AppRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAppRepository(
        apiService: StarlingBankApiService,
        cryptoManager: CryptoManager,
        tokenProvider: TokenProvider,
    ): AppRepository = AppRepositoryImpl(
        apiService = apiService,
        cryptoManager = cryptoManager,
        tokenProvider = tokenProvider
    )
}