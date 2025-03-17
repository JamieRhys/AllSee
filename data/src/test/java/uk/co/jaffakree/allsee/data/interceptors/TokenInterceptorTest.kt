package uk.co.jaffakree.allsee.data.interceptors

import uk.co.jaffakree.allsee.data.local.TokenProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Before
import org.junit.Test
import uk.co.jaffakree.allsee.remote.interceptors.TokenInterceptor

class TokenInterceptorTest {
    private val tokenProvider: TokenProvider = mockk()
    private val chain = mockk<Interceptor.Chain>(relaxed = true)
    private val request = mockk<Request>(relaxed = true)
    private val requestBuilder = mockk<Request.Builder>(relaxed = true )
    private lateinit var underTest: TokenInterceptor

    private val token = "token"

    @Before
    fun setUp() {
        underTest = TokenInterceptor(tokenProvider)

        coEvery { tokenProvider.getToken() } returns token
        every { chain.request() } returns request
        every { request.newBuilder() } returns requestBuilder
        every { requestBuilder.addHeader(any(), any()) } returns requestBuilder
        every { requestBuilder.build() } returns request
        every { chain.proceed(any()) } returns mockk()
    }

    @Test
    fun `When chain intercepted, Then authorisation header is added to request`() {
        // When
        underTest.intercept(chain)

        // Then and Verify
        verify(exactly = 1) { tokenProvider.getToken() }
        verify(exactly = 1) { request.newBuilder() }
        verify(exactly = 1) { requestBuilder.build() }
        verify(exactly = 1) { requestBuilder.addHeader("Authorization", "Bearer $token") }
        verify(exactly = 1) { chain.proceed(request) }
    }
}