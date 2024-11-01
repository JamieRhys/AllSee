package com.sycosoft.allsee.data.remote.service

import com.sycosoft.allsee.data.remote.model.AccountHolderDto
import com.sycosoft.allsee.data.remote.model.AccountHolderNameDto
import retrofit2.http.GET
import retrofit2.http.Header

interface StarlingBankApiService {

    @GET("account-holder")
    suspend fun getAccountHolder(@Header("Authorization") accessToken: String): AccountHolderDto

    @GET("account-holder/name")
    suspend fun getAccountHolderName(@Header("Authorization") accessToken: String): AccountHolderNameDto
}