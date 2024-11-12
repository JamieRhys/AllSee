package com.sycosoft.allsee.data.local

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.random.Random

class TokenProviderTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var tokenProvider: TokenProvider

    private val encryptedTokenKey = stringPreferencesKey("encrypted_token")
    private val sampleToken = "sample_token".toByteArray()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { File(context.cacheDir, "test${Random.nextInt()}.preferences_pb") },
            scope = CoroutineScope(Dispatchers.IO)
        )

        tokenProvider = TokenProvider(dataStore)
    }

    @Test
    fun `Test saveEncryptedToken and getEncryptedToken`() = runTest {
        val result = tokenProvider.saveEncryptedToken(sampleToken)
        assertNotNull(result)

        val encryptedToken = tokenProvider.getEncryptedToken()
        assertNotNull(encryptedToken)

        assertTrue(encryptedToken.contentEquals(sampleToken))
    }

    @Test
    fun `getEncryptedToken returns empty token if not present`() = runTest {
        dataStore.edit { it[encryptedTokenKey] = "" }

        val result = tokenProvider.getEncryptedToken()

        assertTrue(result.isEmpty())
    }


}