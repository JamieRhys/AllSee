package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sycosoft.allsee.data.local.models.PersonEntity

@Dao
interface PersonDao {
    @Query("SELECT * FROM person WHERE id = :id")
    suspend fun getPersonById(id: Int): PersonEntity?

    @Insert
    suspend fun insertPerson(person: PersonEntity): Long
}