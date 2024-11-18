package com.sycosoft.allsee.throwaway

import retrofit2.http.GET

interface MyApiService {
    @GET("endpoint")
    suspend fun getData(): String
}