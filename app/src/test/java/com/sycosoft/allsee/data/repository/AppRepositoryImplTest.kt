package com.sycosoft.allsee.data.repository

import android.util.Log
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.data.remote.models.AccountHolderNameDto
import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import com.sycosoft.allsee.data.remote.models.toDomain
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException

class AppRepositoryImplTest {
    private val apiService: StarlingBankApiService = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)
    private lateinit var underTest: AppRepositoryImpl

    private val errorResponseDto = ErrorResponseDto("error", "Error Description")
    private val apiException = ApiException(errorResponseDto)

    @Before
    fun setUp() {
        underTest = AppRepositoryImpl(apiService, tokenProvider)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    // Save Token
    @Test
    fun `When saveToken is called, Then it should delegate saving token to TokenProvider`() = runBlocking {
        // When and Then
        val token = "dummy_token"
        underTest.saveToken(token)

        // Verify
        coVerify { tokenProvider.saveToken(token) }
    }

    // Account Holder Name
    @Test
    fun `When API succeeds, Then account holder name is returned`() = runBlocking {
        val apiModel = AccountHolderNameDto("John Doe")
        coEvery { apiService.getAccountHolderName() } returns apiModel

        val result = underTest.getAccountHolderName()

        assertEquals(apiModel.toDomain().accountHolderName, result.accountHolderName)
    }

    @Test
    fun `When ApiException thrown, RepositoryException should be thrown and no account holder name returned`() = runBlocking {
        coEvery { apiService.getAccountHolderName() } throws apiException

        try {
            underTest.getAccountHolderName()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(apiException.errorResponse.toDomain(), e.error)
        }
    }

    // Get Account Holder
    @Test
    fun `When API succeeds, Then account holder is returned`() = runBlocking {
        val apiModel = AccountHolderDto("012456789", "INDIVIDUAL")
        coEvery { apiService.getAccountHolder() } returns apiModel

        val result = underTest.getAccountHolder()

        assertEquals(apiModel.toDomain(), result)
        assertEquals(apiModel.toDomain().uid, result.uid)
        assertEquals(apiModel.toDomain().type, result.type)
    }

    @Test
    fun `When ApiException thrown, RepositoryException should be thrown and no account holder returned`() = runBlocking {
        coEvery { apiService.getAccountHolder() } throws apiException

        try {
            underTest.getAccountHolder()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(apiException.errorResponse.toDomain(), e.error)
        }
    }

    // Get Name and Account Type
    @Test
    fun `When API succeeds, Then name and account type is returned`() = runBlocking {
        val accountHolderNameDto = AccountHolderNameDto("John Doe")
        val accountHolderDto = AccountHolderDto("012456789", "INDIVIDUAL")

        coEvery { apiService.getAccountHolderName() } returns accountHolderNameDto
        coEvery { apiService.getAccountHolder() } returns accountHolderDto

        val result = underTest.getNameAndAccountType()
        assertEquals(accountHolderNameDto.toDomain().accountHolderName, result.name)
        assertEquals(accountHolderDto.toDomain().type.displayName, result.type)
    }

    @Test
    fun `When ApiException thrown in getAccountHolderName, Then RepositoryException should be thrown and no object returned`() = runBlocking {
        coEvery { underTest.getAccountHolderName() } throws ApiException(errorResponseDto)

        try {
            underTest.getNameAndAccountType()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(apiException.errorResponse.toDomain(), e.error)
        }
    }

    @Test
    fun `When ApiException thrown in getAccountHolder, Then RepositoryException should be thrown and no object returned`() = runBlocking {
        coEvery { apiService.getAccountHolderName() } returns AccountHolderNameDto("John Doe")
        coEvery { apiService.getAccountHolder() } throws ApiException(errorResponseDto)

        try {
            underTest.getNameAndAccountType()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(apiException.errorResponse.toDomain(), e.error)
        }
    }
}