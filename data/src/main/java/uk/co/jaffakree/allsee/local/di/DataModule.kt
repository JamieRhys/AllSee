package uk.co.jaffakree.allsee.data.local.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import uk.co.jaffakree.allsee.data.local.TokenProvider
import uk.co.jaffakree.allsee.data.local.database.AppDatabase
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideTokenProvider(
        sharedPreferences: SharedPreferences
    ): TokenProvider = TokenProvider(sharedPreferences)

    @Provides
    @Singleton
    fun provideMasterKey(context: Context): MasterKey =
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(context: Context, masterKey: MasterKey): SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = AppDatabase.Companion.getDatabase(context)

    @Provides
    @Singleton
    fun providePersonDao(appDatabase: AppDatabase) = appDatabase.personDao

    @Provides
    @Singleton
    fun provideAccountsDao(appDatabase: AppDatabase) = appDatabase.accountsDao

    @Provides
    @Singleton
    fun provideBalanceDao(appDatabase: AppDatabase) = appDatabase.balanceDao
}