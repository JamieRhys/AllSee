package com.sycosoft.allsee.data.repository

import android.app.Application
import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.mappers.AccountDtoMapper
import com.sycosoft.allsee.data.remote.client.RetrofitClient
import com.sycosoft.allsee.data.remote.model.AccountDto
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.domain.util.AppResult
import com.sycosoft.allsee.domain.util.ResultCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AppRepositoryImpl : AppRepository {
    private val cryptoManager = CryptoManager()
    private val apiService = RetrofitClient("https://api-sandbox.starlingbank.com/api/v2/").starlingBankApiService

    override fun encryptAccessToken(token: String, filesDir: File): Boolean {
        return false
    }

    override suspend fun getAccount(): AppResult<List<Account>> = withContext(Dispatchers.IO) {
        // If not in local DB, fetch from API
        return@withContext try {
            val apiAccounts = apiService.getAccounts("Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21Ty5KjMAz8lSnOoykIb25z2x_YDxCWnLgGbMo2mZ3a2n9fE0MIqdzobj1alvibKOeSLsFJAfFoPpxHOyh97lF_fQgzJu-Jm_sQwaLtT2nTQN3mPRSiYmibOoWMirZqM6qoaUMw_5mSLqvztCyyU5W_Jwp9JIqyaBYChTCz9r_MQGx_K1prc00p9NQWUDSyhZYqGbrUuRClYCw41Pbmi3XMyHI61WVRAiFVUORlA02WSmirDOtgUzRIISOM9SkEO7f3oVNWQ0n1CQoMLRpMCdo8a2qJuSxzsQwszMTLo0SncLlZBY0jd5aR3p4E_zM9CYpYeyUV2yM_KOcPzAqIbDDZMSl_B1HxHsVl5Hvkjr-t8vyGs78Yq1xYGShN6qpoxiEG9zigFqs1gZZAGO2tGWKjhVk1o6WyI3plNBgJcta0GhCz82bc5uAR1Zo9oib03BEPHHxs8BY2sseAsBMBLuKGb5kT_jBvUgRrkQj2IFAjnteaUds_wVvUDsXi-U7DYESYfq8dCTDLMzyza5Y1Ug1bq9j7QN2iLAtWkz8Ad5TiPhxewyocnM3u48Ctox64W51HJg4nw7O_KLGLL2rtYiwqLkzzwARh7P2MHHsfBpynFU64nUn4_8MVhWMylh7aH9mt75F9kQ_mW995z4sBEO76TE0kI_W409sqnpec_PsPc6agxLIEAAA.csYBoFh42Gw2d8z8A2vfzRPuAwmtKv4pA0Fi6QO_m8LRvW0foTR8mXDK2bXX5HN3p7vBxlN1dcWGPk5rubd0ibU0Ydjl2ai4nGV8FO7TTtweaEkop6-GmtsZlFsYiZE6Y8LnUgDmtgbqUle7GM_y-DBfyR6gfQUFnNBL4x8hl4oEHoE947gmxHBDlmlc4_sEhkVdvojeXHqJkviuzi30759HyYl48ytBhT2Hb1O98lPzVuu2O9HUjn1y_tDkvr8HUEQPC_aXWlj_WktSSL41EBXEsDMOybIlntTpjEsjgSKbQLM3SYhSX90OZA_tgmHpRml6ghPJvAiT3oR9FprOFzHGAPotWADLWb-K7OaLHvpbT_h_2Aggcu1h5cRtIRoae70z-jL4653DtGLSZBMUWOIPLv7dGNr-CUlXtejsGHpCBxXU9GDVzdwUMs2rJQP7EU-7Wtzt5l2l5bITieNKhYRRZep2qkB1EYbYVmPVfTXjsQ0ebWhi7Gako-IgJKujXCM6Zdu8hUTFzCuFkxTdEWQ9Zv965DYfp3HG5IEjTCNVOSo0DGq7DdFYGZtJrU1c0pikEyNqsjoA_PtpDkSQa22V-Ahivs9QzgFikjRdpwP-oMxlrpy1My1lJynq7ExAhOwH-wzSg7iUBHIlmvaF5cgTWE-rV4BNzdwZDMalRgI")

            val domainAccountList: MutableList<Account> = mutableListOf()

            apiAccounts.accounts.forEach {
                domainAccountList.add(AccountDtoMapper.mapApiToDomain(it))
            }

            // TODO: Cache the result in local DB once implemented.

            AppResult.Success(domainAccountList.toList())
        } catch (e: Exception) {
            AppResult.Failure(message = "Error fetching account: ${e.localizedMessage}", code = ResultCode.FetchFailure)
        }
    }

    override fun getAccountHolder(): Result<AccountHolder> {
        TODO("Not yet implemented")
    }

    override fun getAccountHolderName(): Result<AccountHolderName> {
        TODO("Not yet implemented")
    }

}