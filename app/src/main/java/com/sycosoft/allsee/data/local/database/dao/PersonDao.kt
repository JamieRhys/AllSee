package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sycosoft.allsee.data.local.models.PersonEntity

@Dao
interface PersonDao {
    @Query("SELECT * FROM person LIMIT 1")
    suspend fun getPerson(): PersonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity): Long
}