package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNameAndAccountTypeUseCaseTest {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetNameAndAccountTypeUseCase

    @Before
    fun setup() {
        underTest = GetNameAndAccountTypeUseCase(repository)
    }

//    @Test
//    fun `Given repository returns success, When invoke is called, Then invoke should return success result`() = runBlocking {
//        // Given
//        val nameAndAccountType = NameAndAccountType(name = "John Doe", type = "Individual")
//        coEvery { repository.getNameAndAccountType() } returns Result.success(nameAndAccountType)
//
//        // When
//        val result = underTest()
//
//        // Then
//        coVerify(exactly = 1) { repository.getNameAndAccountType() }
//        assertTrue(result.isSuccess)
//        assertEquals(nameAndAccountType, result.getOrNull())
//    }
//
//    @Test
//    fun `Given repository returns failure, When invoke is called, Then invoke should return failure result`() = runBlocking {
//        // Given
//        val exception = RepositoryException(ErrorResponse("error", "Error Description"))
//        coEvery { repository.getNameAndAccountType() } returns Result.failure(exception)
//
//        // When
//        val result = underTest()
//
//        // Then
//        coVerify(exactly = 1) { repository.getNameAndAccountType() }
//        assertTrue(result.isFailure)
//        assertEquals(exception, result.exceptionOrNull())
//    }
}