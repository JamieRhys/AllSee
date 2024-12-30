package com.sycosoft.allsee.data.remote.services

import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.models.AccountListDto
import com.sycosoft.allsee.data.remote.models.FullBalanceDto
import com.sycosoft.allsee.data.remote.models.IdentityDto
import retrofit2.http.GET
import retrofit2.http.Path


interface StarlingBankApiService {
    /** Requests a list of accounts from the API. */
    @GET("accounts")
    suspend fun getAccounts(): AccountListDto

    /** Requests a specific account holder from the API associated with the access token */
    @GET("account-holder")
    suspend fun getAccountHolder(): AccountHolderDto

    /** Requests the name of the account holder from the API associated with the access token */
    @GET("account-holder/name")
    suspend fun getAccountHolderName(): AccountHolderNameDto

    @GET("accounts/{accountUid}/balance")
    suspend fun getFullBalance(@Path("accountUid") accountUid: String): FullBalanceDto

    /** Requests the identity of the account holder. This is more in depth than just requesting the name. */
    @GET("identity/individual")
    suspend fun getIdentity(): IdentityDto
}