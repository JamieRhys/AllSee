package com.sycosoft.allsee.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.sycosoft.allsee.data.local.TokenProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {
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
}