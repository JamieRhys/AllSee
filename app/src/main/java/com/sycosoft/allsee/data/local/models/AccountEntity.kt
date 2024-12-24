package com.sycosoft.allsee.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "account_type") val accountType: Int,
    @ColumnInfo(name = "default_category") val defaultCategory: String,
    @ColumnInfo(name = "currency_type") val currency: Int,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "account_name") val name: String,
)