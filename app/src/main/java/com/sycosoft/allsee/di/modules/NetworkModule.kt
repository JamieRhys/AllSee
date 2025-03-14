package com.sycosoft.allsee.di.modules

import dagger.Module
import dagger.Provides
import uk.co.jaffakree.allsee.data.local.TokenProvider
import uk.co.jaffakree.allsee.remote.client.RetrofitClient
import uk.co.jaffakree.allsee.remote.interceptors.ApiHandlerInterceptor
import uk.co.jaffakree.allsee.remote.interceptors.TokenInterceptor
import uk.co.jaffakree.allsee.remote.services.StarlingBankApiService
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