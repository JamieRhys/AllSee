package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.client.RetrofitClient
import com.sycosoft.allsee.data.remote.interceptors.TokenInterceptor
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Module
object TestNetworkModule {
    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Singleton
    fun provideBaseUrl(mockWebServer: MockWebServer): String = mockWebServer.url("/").toString()

    @Provides
    @Singleton
    fun provideTokenInterceptor(tokenProvider: TokenProvider): TokenInterceptor =
        TokenInterceptor(tokenProvider = tokenProvider)

    @Provides
    @Singleton
    fun provideRetrofitClient(baseUrl: String, tokenInterceptor: TokenInterceptor): RetrofitClient =
        RetrofitClient(baseUrl, tokenInterceptor)

    @Provides
    @Singleton
    fun provideStarlingBankApiService(retrofitClient: RetrofitClient): StarlingBankApiService =
        retrofitClient.starlingBankApiService
}