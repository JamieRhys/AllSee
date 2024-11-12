package com.sycosoft.allsee.data

import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.CryptoManagerTest
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.data.repository.AppRepositoryImpl
import com.sycosoft.allsee.domain.repository.AppRepository
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.mockkObject
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test

class AppRepositoryImplTest {
    private lateinit var cryptoManager: CryptoManager
    private lateinit var tokenProvider: TokenProvider
    private lateinit var apiService: StarlingBankApiService
    private lateinit var appRepository: AppRepository

    @Before
    fun setup() {
        cryptoManager = mockkClass(CryptoManager::class)
        tokenProvider = mockk()
        apiService = mockk()
        appRepository = AppRepositoryImpl(
            cryptoManager = cryptoManager,
            tokenProvider = tokenProvider,
            apiService = apiService
        )
    }

    @Test
    fun test() {
        assertNotNull(appRepository)
    }
}