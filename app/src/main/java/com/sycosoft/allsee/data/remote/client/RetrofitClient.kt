package com.sycosoft.allsee.data.remote.client

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.sycosoft.allsee.data.remote.interceptors.ApiHandlerInterceptor
import com.sycosoft.allsee.data.remote.interceptors.TokenInterceptor
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor(
    private val baseUrl: String,
    tokenInterceptor: TokenInterceptor,
    apiHandlerInterceptor: ApiHandlerInterceptor,
) {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(tokenInterceptor)
        .addInterceptor(apiHandlerInterceptor)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val starlingBankApiService: StarlingBankApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(StarlingBankApiService::class.java)
    }
}