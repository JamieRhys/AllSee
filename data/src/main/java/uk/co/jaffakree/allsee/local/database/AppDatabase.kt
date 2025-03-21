package uk.co.jaffakree.allsee.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.co.jaffakree.allsee.data.local.database.dao.AccountsDao
import uk.co.jaffakree.allsee.data.local.database.dao.BalanceDao
import uk.co.jaffakree.allsee.data.local.database.dao.PersonDao
import uk.co.jaffakree.allsee.data.local.models.AccountEntity
import uk.co.jaffakree.allsee.data.local.models.BalanceEntity
import uk.co.jaffakree.allsee.data.local.models.PersonEntity

/**
 * This manages our applications database connection.
 *
 * @author Jamie-Rhys Edwards
 * @since v0.0.1
 *
 */
// Let's define the databases configuration
@Database(
    entities = [
        PersonEntity::class,
        AccountEntity::class,
        BalanceEntity::class,
    ], // List of entity classes that will be a part of the database.
    version = 4, // The version of the database.
    exportSchema = false // TODO: Remove once implemented migrations
)
abstract class AppDatabase: RoomDatabase() {
    abstract val personDao: PersonDao
    abstract val accountsDao: AccountsDao
    abstract val balanceDao: BalanceDao

    companion object {
        // This volatile variable ensures that the value is read from and written to main memory directly,
        // avoiding the use of a CPU cache, making it safe for multithreaded access.
        @Volatile var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // If the instance is null, the database has not yet been initialised.
            if(INSTANCE == null) {
                // Synchronised block ensures that only one thread can execute the code within it
                // at any one time, preventing multiple threads from creating duplicate instances
                // of the database.
                synchronized(this) {
                   if(INSTANCE == null) {
                         INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "allsee.db",
                        )
                            .fallbackToDestructiveMigration() // Set to allow destructive migration,
                            // meaning if the database schema changes, it will drop the existing
                            // database and recreate it.
                            // TODO: Remove fallbackToDestructiveMigration for production release as we do not want this!
                            .build()
                    }
                }
            }

            // If the instance is not null, we've previously initialised the database (creating a
            // singleton class), so return the instance.
            return INSTANCE!!
        }
    }
}