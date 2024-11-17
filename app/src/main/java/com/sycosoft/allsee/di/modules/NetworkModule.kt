package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.client.RetrofitClient
import com.sycosoft.allsee.data.remote.interceptors.ApiHandlerInterceptor
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
        tokenProvider: TokenProvider
    ): TokenInterceptor = TokenInterceptor(
        tokenProvider = tokenProvider,
    )

    @Provides
    @Singleton
    fun provideApiHandlerInterceptor(): ApiHandlerInterceptor = ApiHandlerInterceptor()

    @Provides
    @Singleton
    fun provideStarlingBankApiService(
        tokenInterceptor: TokenInterceptor,
        apiHandlerInterceptor: ApiHandlerInterceptor,
    ): StarlingBankApiService = RetrofitClient(
        baseUrl = "https://api-sandbox.starlingbank.com/api/v2/",
        tokenInterceptor = tokenInterceptor,
        apiHandlerInterceptor = apiHandlerInterceptor,
    ).starlingBankApiService
}