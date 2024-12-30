package com.sycosoft.allsee.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class BalanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("balance_type") val type: Int,
    @ColumnInfo("account_uid") val accountUid: String,
    @ColumnInfo("currency") val currency: Int,
    @ColumnInfo("minor_units") val minorUnits: Int,
)
