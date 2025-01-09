package com.sycosoft.allsee.presentation.usecases

import com.sycosoft.allsee.presentation.models.NameAndAccountType
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.util.UUID

class GetNameAndAccountTypeUseCaseTests {
    private val person = Person(
        uid = UUID.randomUUID(),
        type = AccountHolderType.INDIVIDUAL,
        title = "Mr",
        firstName = "Joe",
        lastName = "Bloggs",
        dob = LocalDate.parse("1975-01-01"),
        email = "joe.blogs@test.com",
        phone = "07932555555"
    )

    private lateinit var underTest: GetNameAndAccountTypeUseCase

    @Before
    fun setup() {
        underTest = GetNameAndAccountTypeUseCase()
    }

    @Test
    fun whenUseCaseCalled_ThenReturnsNameAndAccountTypeObject() = runTest {
        val expected = NameAndAccountType(
            name = "${person.firstName} ${person.lastName}",
            type = person.type.displayName,
        )

        val actual = underTest(person)

        assertEquals(expected, actual)
    }
}