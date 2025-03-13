package com.sycosoft.allsee.domain.usecases

import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import uk.co.jaffakree.allsee.domain.repository.AppRepository
import uk.co.jaffakree.allsee.domain.usecases.GetPersonUseCase
import java.time.LocalDate
import java.util.UUID

class GetPersonUseCaseTests {
    private val repository: AppRepository = mockk(relaxed = true)
    private lateinit var underTest: GetPersonUseCase

    private val person = Person(
        uid = UUID.randomUUID(),
        type = AccountHolderType.INDIVIDUAL,
        title = "Mr",
        firstName = "John",
        lastName = "Doe",
        dob = LocalDate.now(),
        email = "john.doe@test.com",
        phone = "1234567890"
    )

    @Before
    fun setup() {
        underTest = GetPersonUseCase(repository)
    }

    @Test
    fun whenUseCaseCalled_ThenInvokeDelegatesToRepositoryAndReturnsPerson() = runTest {
        val expected = person.copy()

        coEvery { repository.getPerson() } returns person

        val actual = underTest()

        assertEquals(expected, actual)
    }

    @Test(expected = RepositoryException::class)
    fun whenUseCaseCalled_GivenRepositoryExceptionIsThrown_ThenExceptionIsPropagatedToCaller() = runTest {
        val exception = RepositoryException(ErrorResponse(error = "error", errorDescription = "errorDescription"))
        coEvery { repository.getPerson() } throws exception

        underTest()
    }
}