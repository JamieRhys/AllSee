package com.sycosoft.allsee.presentation.usecases

import com.sycosoft.allsee.presentation.models.AccountDetails
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import uk.co.jaffakree.allsee.domain.models.types.AccountType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

class GetAccountDetailsUseCaseTests {
    private val account = Account(
        accountUid = UUID.randomUUID(),
        accountType = AccountType.PRIMARY,
        defaultCategory = UUID.randomUUID(),
        currency = CurrencyType.GBP,
        createdAt = OffsetDateTime.now(),
        name = "Personal",
        accountIdentifier = "12345678",
        bankIdentifier = "123456",
        iban = "GB123456789012345678",
        bic = "ABCD12"
    )
    private val person = Person(
        uid = UUID.randomUUID(),
        type = AccountHolderType.INDIVIDUAL,
        title = "Mr",
        firstName = "Joe",
        lastName = "Bloggs",
        dob = LocalDate.parse("1975-01-01"),
        email = "joe.blogs@example.com",
        phone = "07932555555"
    )

    private lateinit var underTest: GetAccountDetailsUseCase

    @Before
    fun setup() {
        underTest = GetAccountDetailsUseCase()
    }

    @Test
    fun whenUseCaseCalled_ThenReturnsAccountDetailsObject() = runTest {
        val expected = AccountDetails(
            name = "${person.firstName} ${person.lastName}",
            type = person.type.displayName,
            accountNumber = account.accountIdentifier,
            sortCode = account.bankIdentifier,
            iban = account.iban,
            bic = account.bic,
        )

        val actual = underTest(account, person)

        assertEquals(expected, actual)
    }
}