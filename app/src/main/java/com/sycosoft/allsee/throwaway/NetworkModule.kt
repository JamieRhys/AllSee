package com.sycosoft.allsee.throwaway

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl("https://api.example.com").build()

    @Provides
    fun provideMyApiService(retrofit: Retrofit): MyApiService = retrofit.create(MyApiService::class.java)
}