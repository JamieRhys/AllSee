package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sycosoft.allsee.data.local.models.PersonEntity

@Dao
interface PersonDao {
    /** Queries the database and returns the [PersonEntity] if present. This is limited to 1 as only
     * one customer should be present at any one time.
     *
     * @return [PersonEntity] if present, null otherwise.
     */
    @Query("SELECT * FROM person LIMIT 1")
    suspend fun getPerson(): PersonEntity?

    /** Inserts the given [PersonEntity] into the database. If one exists, this is replaced.
     *
     * @return The unique ID number for the row that was inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity): Long
}