package com.sycosoft.allsee.data.repository

import android.util.Log
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

class AppRepositoryImplTest {
    private val apiService: StarlingBankApiService = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)
    private lateinit var underTest: AppRepositoryImpl

    private val validName = "John Doe"
    private val errorResponse = ErrorResponseDto(error = "invalid_token", errorDescription = "Invalid token")
    private val errorBody = "{\"error\": \"invalid_token\", \"error_description\":\"Invalid token\"}"

    @Before
    fun setUp() {
        underTest = AppRepositoryImpl(apiService, tokenProvider)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun `When account holder name requested, Then valid account holder name is returned`() {
        // Setup
        coEvery { apiService.getAccountHolderName() } returns AccountHolderNameDto(validName)

        runBlocking {
            // When
            val result = underTest.getAccountHolderName()

            // Then and verify
            coVerify(exactly = 1) { apiService.getAccountHolderName() }
            assertTrue(result.isSuccess)
            assertEquals(validName, result.getOrNull()?.accountHolderName)
        }
    }

    @Test
    fun `When account holder name requested and provided invalidToken, Then error message is returned`() {
        // Setup
        coEvery { apiService.getAccountHolderName() } throws ApiException(errorResponse)

        runBlocking {
            // When
            val result = underTest.getAccountHolderName()

            // Then and verify
            coVerify(exactly = 1) { apiService.getAccountHolderName() }
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()

            assertNotNull(exception)
            assertTrue(exception is RepositoryException)

            exception as RepositoryException
            assertEquals(errorResponse.error, exception.error.error)
            assertEquals(errorResponse.errorDescription, exception.error.errorDescription)
        }
    }

    @Test
    fun `When unknown error occurs, Then correct RepositoryException returned with relevant details`() {
        // Setup
        coEvery { apiService.getAccountHolderName() } throws CancellationException()

        runBlocking {
            // When
            val result = underTest.getAccountHolderName()

            // Then and Verify
            coVerify(exactly = 1) { apiService.getAccountHolderName() }
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()

            assertNotNull(exception)
            assertTrue(exception is RepositoryException)

            exception as RepositoryException
            assertEquals("unknown_error", exception.error.error)
            assertEquals("Unexpected error while handling request", exception.error.errorDescription)
        }
    }
}