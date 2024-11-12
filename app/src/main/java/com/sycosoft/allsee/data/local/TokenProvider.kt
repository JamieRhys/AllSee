package com.sycosoft.allsee.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import android.util.Base64

class TokenProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val encryptedTokenKey = stringPreferencesKey("encrypted_token")

    /** Returns the encrypted token key if present in the [Preferences] [DataStore]. Otherwise,
     * returns empty [ByteArray].
     */
    suspend fun getEncryptedToken(): ByteArray {
        return dataStore.data.first { it.contains(encryptedTokenKey) }[encryptedTokenKey]?.let {
            Base64.decode(it, Base64.DEFAULT)
        } ?: ByteArray(0)
    }

    /** Saves the provided (and encrypted) token into the [Preferences] [DataStore]. */
    suspend fun saveEncryptedToken(encryptedToken: ByteArray) {
        dataStore.edit { preferences ->
            preferences[encryptedTokenKey] = Base64.encodeToString(encryptedToken, Base64.DEFAULT)
        }
    }
}