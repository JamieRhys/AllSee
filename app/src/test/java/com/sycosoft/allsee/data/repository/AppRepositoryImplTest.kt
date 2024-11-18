package com.sycosoft.allsee.data.repository

import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.repository.AppResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class AppRepositoryImplTest {
    private val apiService: StarlingBankApiService = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)
    private lateinit var underTest: AppRepositoryImpl

    private val validName = "John Doe"
    private val errorResponse = ErrorResponse(error = "invalid_token", errorDescription = "Invalid token")
    private val errorBody = "{\"error\": \"invalid_token\", \"error_description\":\"Invalid token\"}"

    @Before
    fun setUp() {
        underTest = AppRepositoryImpl(apiService, tokenProvider)
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
            assertTrue(result is AppResult.Success)
            assertEquals(validName, (result as AppResult.Success).data.accountHolderName)
        }
    }

    @Test
    fun `When account holder name requested and provided invalidToken, Then error message is returned`() {
        // Setup
        coEvery { apiService.getAccountHolderName() } throws HttpException(Response.error<Any>(401, errorBody.toResponseBody()))

        runBlocking {
            // When
            val result = underTest.getAccountHolderName()

            // Then and verify
            coVerify(exactly = 1) { apiService.getAccountHolderName() }
            assertTrue(result is AppResult.Error)
            assertEquals(errorResponse.error, (result as AppResult.Error).errorResponse.error)
            assertEquals(errorResponse.errorDescription, result.errorResponse.errorDescription)
        }
    }
}