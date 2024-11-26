package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import com.sycosoft.allsee.domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetAccountHolderUseCaseTest {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetAccountHolderUseCase

    @Before
    fun setUp() {
        underTest = GetAccountHolderUseCase(repository)
    }

    @Test
    fun `Given repository returns success, When invoke is called, Then invoke should return success result`() = runBlocking {
        // Given
        val expected = AccountHolder(uid = "1234567890", type = AccountHolderType.INDIVIDUAL)
        coEvery { repository.getAccountHolder() } returns expected

        // When
        val actual = underTest()

        // Then
        coVerify(exactly = 1) { repository.getAccountHolder() }
        assertEquals(expected, actual)
    }

    @Test
    fun `Given repository returns failure, When invoke is called, Then invoke should return failure result`() = runBlocking {
        // Given
        val expected = RepositoryException(ErrorResponse("error", "Error Description"))
        coEvery { repository.getAccountHolder() } throws expected

        // When and Verify
        try {
            underTest()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(expected, e)
        } catch(e: Exception) {
            fail("Unexpected exception thrown: ${e.message}")
        }
    }
}