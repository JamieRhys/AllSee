package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.client.RetrofitClient
import com.sycosoft.allsee.data.remote.interceptors.TokenInterceptor
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideTokenInterceptor(
        cryptoManager: CryptoManager,
        tokenProvider: TokenProvider
    ): TokenInterceptor = TokenInterceptor(
        cryptoManager = cryptoManager,
        tokenProvider = tokenProvider,
    )

    @Provides
    @Singleton
    fun provideStarlingBankApiService(
        tokenInterceptor: TokenInterceptor,
    ): StarlingBankApiService = RetrofitClient(
        baseUrl = "https://api-sandbox.starlingbank.com/api/v2/",
        tokenInterceptor = tokenInterceptor,
    ).starlingBankApiService
}