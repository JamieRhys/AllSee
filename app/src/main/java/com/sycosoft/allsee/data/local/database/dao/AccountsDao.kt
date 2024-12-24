package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sycosoft.allsee.data.local.models.AccountEntity

@Dao
interface AccountsDao {
    @Query("SELECT * FROM accounts")
    suspend fun getAccounts(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>): List<Long>
}