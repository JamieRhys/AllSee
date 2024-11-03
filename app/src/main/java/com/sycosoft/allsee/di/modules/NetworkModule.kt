package com.sycosoft.allsee.di.modules

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sycosoft.allsee.data.remote.client.RetrofitClient
import com.sycosoft.allsee.data.remote.service.StarlingBankApiService
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitClient(): RetrofitClient = RetrofitClient("https://api-sandbox.starlingbank.com/api/v2/")

    @Provides
    @Singleton
    fun provideStarlingBankApiService(retrofitClient: RetrofitClient): StarlingBankApiService = retrofitClient.starlingBankApiService
}