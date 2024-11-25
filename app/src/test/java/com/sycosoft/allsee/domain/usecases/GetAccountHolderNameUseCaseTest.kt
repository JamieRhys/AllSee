package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolderName
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAccountHolderNameUseCaseTest {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetAccountHolderNameUseCase

    @Before
    fun setUp() {
        underTest = GetAccountHolderNameUseCase(repository)
    }

//    @Test
//    fun `Given repository returns success, When invoke is called, Then invoke should return success result`() = runBlocking {
//        // Given
//        val accountHolderName = AccountHolderName(accountHolderName = "John Doe")
//        coEvery { repository.getAccountHolderName() } returns Result.success(accountHolderName)
//
//        // When
//        val result = underTest()
//
//        // Then
//        coVerify(exactly = 1) { repository.getAccountHolderName() }
//        assertTrue(result.isSuccess)
//        assertEquals(accountHolderName, result.getOrNull())
//    }
//
//    @Test
//    fun `Given repository returns failure, When invoke is called, Then invoke should return failure result`() = runBlocking {
//        // Given
//        val exception = RepositoryException(ErrorResponse("error", "Error Description"))
//        coEvery { repository.getAccountHolderName() } returns Result.failure(exception)
//
//        // When
//        val result = underTest()
//
//        // Then
//        coVerify(exactly = 1) { repository.getAccountHolderName() }
//        assertTrue(result.isFailure)
//        assertEquals(exception, result.exceptionOrNull())
//    }
}