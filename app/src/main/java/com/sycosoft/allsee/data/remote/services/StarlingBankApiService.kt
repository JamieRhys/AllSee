package com.sycosoft.allsee.data.remote.services

import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.models.AccountListDto
import com.sycosoft.allsee.data.remote.models.IdentityDto
import retrofit2.http.GET


interface StarlingBankApiService {
    @GET("accounts")
    suspend fun getAccounts(): AccountListDto

    @GET("account-holder")
    suspend fun getAccountHolder(): AccountHolderDto

    @GET("account-holder/name")
    suspend fun getAccountHolderName(): AccountHolderNameDto

    @GET("identity/individual")
    suspend fun getIdentity(): IdentityDto
}