package uk.co.jaffakree.allsee.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.jaffakree.allsee.data.local.models.AccountEntity

@Dao
interface AccountsDao {
    /** Queries the database and returns any accounts present. If no accounts are present, an empty
     * list is returned.
     */
    @Query("SELECT * FROM accounts")
    suspend fun getAccounts(): List<AccountEntity>

    /** Inserts the provided [AccountEntity] into the database. If successful, it will return a list
     * of rows that have changed.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>): List<Long>
}