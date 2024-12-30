package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sycosoft.allsee.data.local.models.BalanceEntity

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balances WHERE account_uid = :accountUid")
    suspend fun getBalances(accountUid: String): List<BalanceEntity>

    @Query("SELECT * FROM balances WHERE account_uid = :accountUid AND balance_type = :type")
    suspend fun getBalanceFromType(accountUid: String, type: Int): BalanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balances: List<BalanceEntity>): List<Long>
}