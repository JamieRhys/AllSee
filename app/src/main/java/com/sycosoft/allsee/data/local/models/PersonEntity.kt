package com.sycosoft.allsee.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class PersonEntity(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "type")val type: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "dob") val dob: String,
    @ColumnInfo(name = "email")val email: String,
    @ColumnInfo(name = "phone") val phone: String,
)