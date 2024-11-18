package com.sycosoft.allsee.data.remote.interceptors

import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ApiHandlerInterceptorTest {
    private val chain = mockk<Interceptor.Chain>(relaxed = true)
    private val request = mockk<Request>(relaxed = true)
    private val requestBuilder = mockk<Request.Builder>(relaxed = true)
    private lateinit var underTest: ApiHandlerInterceptor

    private val okJsonString = "{ \"name\": \"John Doe\" }"
    private val invalidTokenJsonString = "{ \"error\": \"invalid_token\", \"error_description\": \"Could not validate provided access token\" }"
    private val missingTokenJsonString = "{ \"error\": \"invalid_token\", \"error_description\": \"No access token provided in request. `Header: Authorization` must be set\"}"
    private val noInternetErrorResponseDto = ErrorResponseDto(
        error = "no_internet",
        errorDescription = "Unable to contact server. Please check your internet connection."
    )

    private val okResponse = okhttp3.Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(200)
        .message("OK")
        .body(okJsonString.toResponseBody("application/json".toMediaType()))
        .build()

    private val invalidTokenResponse = okhttp3.Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(403)
        .message("Forbidden")
        .body(invalidTokenJsonString.toResponseBody("application/json".toMediaType()))
        .build()

    private val missingTokenResponse = okhttp3.Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(403)
        .message("Forbidden")
        .body(missingTokenJsonString.toResponseBody("application/json".toMediaType()))
        .build()

    @Before
    fun setUp() {
        underTest = ApiHandlerInterceptor()

        every { chain.request() } returns request
        every { request.newBuilder() } returns requestBuilder
        every { requestBuilder.addHeader(any(), any()) } returns requestBuilder
        every { requestBuilder.build() } returns request
    }

    @Test
    fun `When response intercepted, Then response is returned due to successful request`() {
        // Setup
        every { chain.proceed(any()) } returns okResponse

        // When
        val result = underTest.intercept(chain)

        // Then and Verify
        verify(exactly = 1) { chain.proceed(request) }
        assertEquals(200, result.code)
        assertEquals(okJsonString, result.body?.string())
        assertEquals("OK", result.message)
    }

    @Test
    fun `When response intercepted, Then ApiException is thrown due to invalid token`() {
        // Setup
        every { chain.proceed(any()) } returns invalidTokenResponse

        // When
        try {
            underTest.intercept(chain)
        } catch (e: ApiException) {
            // Verify
            assertEquals("invalid_token", e.errorResponse.error)
            assertEquals("Could not validate provided access token", e.errorResponse.errorDescription)
        }
    }

    @Test
    fun `When response intercepted, Then ApiException is thrown due to missing token`() {
        // Setup
        every { chain.proceed(any()) } returns missingTokenResponse

        // When
        try {
            underTest.intercept(chain)
        } catch (e: ApiException) {
            // Verify
            assertEquals("invalid_token", e.errorResponse.error)
            assertEquals("No access token provided in request. `Header: Authorization` must be set", e.errorResponse.errorDescription)
        }
    }

    @Test
    fun `When response intercepted, Then ApiException is thrown due to no internet`() {
        // Setup
        every { chain.proceed(any()) } throws UnknownHostException()

        // When
        try {
            underTest.intercept(chain)
        } catch (e: ApiException) {
            // Verify
            assertEquals(noInternetErrorResponseDto.error, e.errorResponse.error)
            assertEquals(noInternetErrorResponseDto.errorDescription, e.errorResponse.errorDescription)
        }
    }
}