package com.sycosoft.allsee.data.remote.service

import com.sycosoft.allsee.data.remote.model.ApiAccountHolder
import com.sycosoft.allsee.data.remote.model.ApiAccountHolderName
import retrofit2.http.GET
import retrofit2.http.Header

interface StarlingBankApiService {

    @GET("account-holder")
    suspend fun getAccountHolder(@Header("Authorization") accessToken: String): ApiAccountHolder

    @GET("account-holder/name")
    suspend fun getAccountHolderName(@Header("Authorization") accessToken: String): ApiAccountHolderName
}