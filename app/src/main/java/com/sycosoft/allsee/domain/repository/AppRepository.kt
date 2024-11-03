package com.sycosoft.allsee.domain.repository

import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.util.AppResult
import java.io.File

interface AppRepository {

    fun encryptAccessToken(token: String, filesDir: File): Boolean

    suspend fun getAccount(): AppResult<List<Account>>

    fun getAccountHolder(): Result<AccountHolder>

    fun getAccountHolderName(): Result<AccountHolderName>
}