package com.sycosoft.allsee.domain.usecases

import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.NameAndAccountType
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import com.sycosoft.allsee.domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetNameAndAccountTypeUseCaseTest {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetNameAndAccountTypeUseCase

    @Before
    fun setup() {
        underTest = GetNameAndAccountTypeUseCase()
    }

    @Test
    fun `Given valid person object, Then invoke should return NameAndAccountType`() = runBlocking {
        // Given
        val person = Person(
            uid = "0123456789",
            type = AccountHolderType.INDIVIDUAL,
            title = "Mr",
            firstName = "John",
            lastName = "Doe",
            dob = LocalDate.parse("1990-01-01"),
            email = "john.doe@example.com",
            phone = "1234567890"
        )
        val expected = NameAndAccountType(name = "John Doe", type = "Individual")
        coEvery { repository.getNameAndAccountType() } returns expected

        // When
        val actual = underTest(person)

        // Then
        assertEquals(expected, actual)
    }
}