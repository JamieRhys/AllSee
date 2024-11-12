package com.sycosoft.allsee.data.remote.interceptors

import android.util.Base64
import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.di.components.DaggerTestAppComponent
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import javax.inject.Inject

class TokenInterceptorTest {
    // TODO: Test Interceptor?
}
