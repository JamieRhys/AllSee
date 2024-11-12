package com.sycosoft.allsee.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.client.RetrofitClient
import com.sycosoft.allsee.data.remote.interceptors.TokenInterceptor
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.mockwebserver.MockWebServer
import org.mockito.Mockito.mock
import java.io.File
import javax.inject.Singleton
import kotlin.random.Random

@Module
object TestNetworkModule {
    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Singleton
    fun provideBaseUrl(mockWebServer: MockWebServer): String = mockWebServer.url("/").toString()

    @Provides
    @Singleton
    fun provideTokenInterceptor(cryptoManager: CryptoManager, tokenProvider: TokenProvider): TokenInterceptor =
        TokenInterceptor(cryptoManager = cryptoManager, tokenProvider = tokenProvider)

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { File(context.cacheDir, "test${Random.nextInt()}.preferences_pb") },
        scope = CoroutineScope(Dispatchers.IO)
    )

    @Provides
    @Singleton
    fun provideTokenProvider(dataStore: DataStore<Preferences>): TokenProvider = TokenProvider(dataStore)

    @Provides
    @Singleton
    fun provideRetrofitClient(baseUrl: String, tokenInterceptor: TokenInterceptor): RetrofitClient =
        RetrofitClient(baseUrl, tokenInterceptor)

    @Provides
    @Singleton
    fun provideStarlingBankApiService(retrofitClient: RetrofitClient): StarlingBankApiService =
        retrofitClient.starlingBankApiService
}