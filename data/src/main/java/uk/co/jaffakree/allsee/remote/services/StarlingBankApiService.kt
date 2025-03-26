package uk.co.jaffakree.allsee.remote.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uk.co.jaffakree.allsee.remote.models.AccountHolderDto
import uk.co.jaffakree.allsee.remote.models.AccountHolderNameDto
import uk.co.jaffakree.allsee.remote.models.AccountIdentifierDto
import uk.co.jaffakree.allsee.remote.models.AccountListDto
import uk.co.jaffakree.allsee.remote.models.FeedItemsDto
import uk.co.jaffakree.allsee.remote.models.FullBalanceDto
import uk.co.jaffakree.allsee.remote.models.IdentityDto
import java.time.OffsetDateTime
import java.util.UUID


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
    suspend fun getFullBalance(@Path("accountUid") accountUid: UUID): FullBalanceDto

    /** Requests the identity of the account holder. This is more in depth than just requesting the name. */
    @GET("identity/individual")
    suspend fun getIdentity(): IdentityDto

    /** Requests the identifiers of the account.
     *
     * This includes the account number (account identifier), sort code (bank identifier), IBAN and BIC.
     */
    @GET("accounts/{accountUid}/identifiers")
    suspend fun getAccountIdentifiers(@Path("accountUid") accountUid: UUID): AccountIdentifierDto

    @GET("feed/account/{accountUid}/category/{categoryUid}")
    suspend fun getTransactionFeed(
        @Path("accountUid") accountUid: UUID,
        @Path("categoryUid") categoryUid: UUID,
        @Query("changesSince") changesSince: OffsetDateTime,
    ): FeedItemsDto
}