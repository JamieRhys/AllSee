package com.sycosoft.allsee.data.remote.service

import com.sycosoft.allsee.data.remote.model.AccountDto
import com.sycosoft.allsee.data.remote.model.AccountHolderDto
import com.sycosoft.allsee.data.remote.model.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.model.AccountListDto
import retrofit2.http.GET
import retrofit2.http.Header

interface StarlingBankApiService {

    @GET("accounts")
    suspend fun getAccounts(@Header("Authorization") accessToken: String): AccountListDto

    @GET("account-holder")
    suspend fun getAccountHolder(@Header("Authorization") accessToken: String): AccountHolderDto

    @GET("account-holder/name")
    suspend fun getAccountHolderName(@Header("Authorization") accessToken: String): AccountHolderNameDto
}