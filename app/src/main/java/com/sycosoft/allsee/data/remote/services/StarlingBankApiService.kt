package com.sycosoft.allsee.data.remote.services

import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.models.AccountListDto
import retrofit2.http.GET
import retrofit2.http.Header


interface StarlingBankApiService {
    @GET("accounts")
    suspend fun getAccounts(): AccountListDto

    @GET("account-holder")
    suspend fun getAccountHolder(@Header("Authorization") accessToken: String): AccountHolderDto

    @GET("account-holder/name")
    suspend fun getAccountHolderName(): AccountHolderNameDto
}