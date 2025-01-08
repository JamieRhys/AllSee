package com.sycosoft.allsee.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
import com.sycosoft.allsee.data.local.DatabaseException
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.local.database.dao.AccountsDao
import com.sycosoft.allsee.data.local.database.dao.BalanceDao
import com.sycosoft.allsee.data.local.database.dao.PersonDao
import com.sycosoft.allsee.data.local.models.PersonEntity
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.AccountDto
import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.data.remote.models.AccountIdentifierDto
import com.sycosoft.allsee.data.remote.models.AccountListDto
import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import com.sycosoft.allsee.data.remote.models.IdentityDto
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.mappers.AccountHolderMapper
import com.sycosoft.allsee.domain.mappers.AccountsMapper
import com.sycosoft.allsee.domain.mappers.BalanceMapper
import com.sycosoft.allsee.domain.mappers.ErrorResponseMapper
import com.sycosoft.allsee.domain.mappers.FullBalanceMapper
import com.sycosoft.allsee.domain.mappers.IdentityMapper
import com.sycosoft.allsee.domain.mappers.PersonMapper
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.Person
import com.sycosoft.allsee.domain.models.types.AccountHolderType
import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

class AppRepositoryImplTest {
    private val apiService: StarlingBankApiService = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)
    private val personDao: PersonDao = mockk(relaxed = true)
    private val accountsDao: AccountsDao = mockk(relaxed = true)
    private val balanceDao: BalanceDao = mockk(relaxed = true)
    private val identityMapper: IdentityMapper = IdentityMapper()
    private val balanceMapper: BalanceMapper = BalanceMapper()
    private val fullBalanceMapper: FullBalanceMapper = FullBalanceMapper()
    private val personMapper: PersonMapper = PersonMapper()
    private lateinit var underTest: AppRepositoryImpl

    private val errorResponseDto = ErrorResponseDto("error", "Error Description")
    private val apiException = ApiException(errorResponseDto)
    private val validPerson = Person(
        uid = UUID.randomUUID(),
        type = AccountHolderType.INDIVIDUAL,
        title = "Mr",
        firstName = "Joe",
        lastName = "Bloggs",
        dob = LocalDate.parse("1975-01-01"),
        email = "joe.bloggs@allsee.com",
        phone = "0192"
    )
    private val validAccount = Account(
        accountUid = UUID.randomUUID(),
        accountType = AccountType.PRIMARY,
        defaultCategory = UUID.randomUUID(),
        currency = CurrencyType.GBP,
        createdAt = OffsetDateTime.now(),
        name = "Personal",
        accountIdentifier = "12345678",
        bankIdentifier = "123456",
        iban = "GB12345612345678",
        bic = "SIC1234221",
    )

    @Before
    fun setUp() {
        underTest = AppRepositoryImpl(
            apiService = apiService,
            personDao = personDao,
            accountsDao = accountsDao,
            balanceDao = balanceDao,
            tokenProvider = tokenProvider,
            identityMapper = identityMapper,
            personMapper = personMapper,
            balanceMapper = balanceMapper,
            fullBalanceMapper = fullBalanceMapper
        )

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
    }

    // =============================================================================================
    // == Save Accounts                                                                           ==
    // =============================================================================================

    @Test
    fun `When saving accounts, Then objects should be saved and row returned`() = runBlocking {
        val accounts: List<Account> = listOf(
            validAccount.copy(),
            validAccount.copy(accountUid = UUID.randomUUID()),
            validAccount.copy(accountUid = UUID.randomUUID()),
            validAccount.copy(accountUid = UUID.randomUUID()),
        )

        val expected = listOf(1L, 2L, 3L, 4L)
        coEvery { accountsDao.insertAccounts(any()) } returns expected

        val actual = underTest.saveAccounts(accounts)

        coVerify(exactly = 1) { accountsDao.insertAccounts(any()) }
        assertEquals(expected, actual)
    }

    @Test(expected = RepositoryException::class)
    fun `When saving accounts and database throws SQLiteException, Then RepositoryException should be thrown`() = runTest {
        coEvery { accountsDao.insertAccounts(any()) } throws SQLiteException()
        val accounts: List<Account> = listOf(
            validAccount.copy(),
            validAccount.copy(accountUid = UUID.randomUUID()),
            validAccount.copy(accountUid = UUID.randomUUID()),
            validAccount.copy(accountUid = UUID.randomUUID()),
        )

        underTest.saveAccounts(accounts)
    }

    // =============================================================================================
    // == Save Person                                                                             ==
    // =============================================================================================

    @Test
    fun `When saving valid person object, Then object should be saved and row returned`() = runBlocking {
        val person = validPerson.copy()

        val expected = 1L
        coEvery { personDao.insertPerson(any()) } returns expected

        val actual = underTest.savePerson(person)

        coVerify(exactly = 1) { personDao.insertPerson(any()) }
        assertEquals(expected, actual)
    }

    @Test(expected = RepositoryException::class)
    fun `When saving person and database throws SQLiteException, Then RepositoryException should be thrown`() = runTest {
        coEvery { personDao.insertPerson(any()) } throws SQLiteException()
        val person = validPerson.copy()

        underTest.savePerson(person)
    }

    // =============================================================================================
    // == Save Token                                                                              ==
    // =============================================================================================

    @Test
    fun `When saveToken is called, Then it should delegate saving token to TokenProvider`() = runBlocking {
        // When and Then
        val token = "dummy_token"
        underTest.saveToken(token)

        // Verify
        coVerify { tokenProvider.saveToken(token) }
    }

    // =============================================================================================
    // == Get Accounts                                                                            ==
    // =============================================================================================

    @Test
    fun `When API succeeds, Then accounts are returned`() = runTest {
        val apiAccountModel = AccountDto(
            accountUid = UUID.randomUUID().toString(),
            accountType = AccountType.PRIMARY.name,
            defaultCategory = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.name,
            createdAt = OffsetDateTime.now().toString(),
            name = "Personal"
        )
        val apiAccountListDto = AccountListDto(
            accounts = listOf(apiAccountModel),
        )
        val apiIdentifierModel = AccountIdentifierDto(
            accountIdentifier = "12345678",
            bankIdentifier = "123456",
            iban = "GB12345612345678",
            bic = "SIC1234221",
            accountIdentifiers = emptyList(),
        )
        val expected: List<Account> = listOf(
            AccountsMapper.toDomain(apiAccountModel, apiIdentifierModel)
        )

        coEvery { accountsDao.getAccounts() } returns emptyList()
        coEvery { apiService.getAccountIdentifiers(any()) } returns apiIdentifierModel
        coEvery { apiService.getAccounts() } returns apiAccountListDto

        val actual = underTest.getAccounts()

        assertEquals(expected, actual)
    }

    @Test
    fun `When ApiException thrown, RepositoryException should be thrown and no accounts returned`() = runTest {
        coEvery { accountsDao.getAccounts() } returns emptyList()
        coEvery { apiService.getAccounts() } throws apiException

        try {
            underTest.getAccounts()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(ErrorResponseMapper.toDomain(apiException.errorResponse), e.error)
        }
    }

    @Test(expected = RepositoryException::class)
    fun `When DatabaseException thrown, RepositoryException should be thrown and no accounts returned`() = runTest {
        coEvery { accountsDao.getAccounts() } throws SQLiteException()

        underTest.getAccounts()
    }

    // =============================================================================================
    // == Get Account Holder                                                                      ==
    // =============================================================================================

    @Test
    fun `When API succeeds, Then account holder is returned`() = runBlocking {
        val apiModel = AccountHolderDto("012456789", "INDIVIDUAL")
        coEvery { apiService.getAccountHolder() } returns apiModel

        val result = underTest.getAccountHolder()

        assertEquals(AccountHolderMapper.toDomain(apiModel), result)
    }

    @Test
    fun `When ApiException thrown, RepositoryException should be thrown and no account holder returned`() = runBlocking {
        coEvery { apiService.getAccountHolder() } throws apiException

        try {
            underTest.getAccountHolder()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(ErrorResponseMapper.toDomain(apiException.errorResponse), e.error)
        }
    }

    // =============================================================================================
    // == Get Identity                                                                            ==
    // =============================================================================================
    @Test
    fun `When API succeeds, Then person identity is returned`() = runBlocking {
        val identityDto = IdentityDto(
            title = "Mr",
            firstName = "Joe",
            lastName = "Bloggs",
            dateOfBirth = "1975-01-01",
            email = "joe.bloggs@example.com",
            phone = "0123456789",
        )

        coEvery { apiService.getIdentity() } returns identityDto

        val actual = underTest.getIdentity()
        val expected = identityMapper.toDomain(identityDto)

        assertEquals(expected, actual)
    }

    @Test
    fun `When ApiException thrown, RepositoryException should be thrown and no identity returned`() = runBlocking {
        coEvery { apiService.getIdentity() } throws apiException

        try {
            underTest.getIdentity()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(ErrorResponseMapper.toDomain(apiException.errorResponse), e.error)
        }
    }

    // =============================================================================================
    // == Get Person                                                                              ==
    // =============================================================================================
    @Test
    fun `When Database returns valid object, Then database person is returned`() = runBlocking {
        val accountHolderDto = AccountHolderDto(UUID.randomUUID().toString(), "INDIVIDUAL")
        val identityDto = IdentityDto("Mr", "John", "Doe", "1975-01-01", "joe.bloggs@example.com", "0123456789")
        val expected = personMapper.toDomain(
            PersonEntity(
                uid = accountHolderDto.accountHolderUid,
                type = AccountHolderMapper.toDomain(accountHolderDto).type.toString(),
                title = identityDto.title,
                firstName = identityDto.firstName,
                lastName = identityDto.lastName,
                dob = identityDto.dateOfBirth,
                email = identityDto.email,
                phone = identityDto.phone
            )
        )

        coEvery { personDao.getPerson() } returns null
        coEvery { apiService.getAccountHolder() } returns accountHolderDto
        coEvery { apiService.getIdentity() } returns identityDto

        val actual = underTest.getPerson()

        assertEquals(expected, actual)
    }

    @Test
    fun `When DatabaseException thrown during saving of person, Then RepositoryException should be returned and no person object returned`() = runBlocking {
        val expected = DatabaseException(ErrorResponse("error", "Error Response"))
        coEvery { personDao.getPerson() } throws expected

        try {
            underTest.getPerson()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(expected.errorResponse, e.error)
        }
    }

    @Test
    fun `When API succeeds, Then person object is returned`() = runBlocking {
        val accountHolderDto = AccountHolderDto(UUID.randomUUID().toString(), "INDIVIDUAL")
        val identityDto = IdentityDto("Mr", "John", "Doe", "1975-01-01", "joe.bloggs@example.com", "0123456789")
        val identity = identityMapper.toDomain(identityDto)
        val accountHolder = AccountHolderMapper.toDomain(accountHolderDto)

        coEvery { personDao.getPerson() } returns null
        coEvery { apiService.getAccountHolder() } returns accountHolderDto
        coEvery { apiService.getIdentity() } returns identityDto

        val actual = underTest.getPerson()
        val expected = Person(
            uid = UUID.fromString(accountHolder.uid),
            type = accountHolder.type,
            title = identity.title,
            firstName = identity.firstName,
            lastName = identity.lastName,
            dob = identity.dob,
            email = identity.email,
            phone = identity.phone
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `When ApiException thrown in getAccountHolder from getPerson, Then RepositoryException should be thrown and no object returned`() = runBlocking {
        coEvery { personDao.getPerson() } returns null
        coEvery { apiService.getAccountHolder() } throws ApiException(errorResponseDto)

        try {
            underTest.getPerson()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(ErrorResponseMapper.toDomain(apiException.errorResponse), e.error)
        }
    }

    @Test
    fun `When ApiException thrown in getIdentity from getPerson, Then RepositoryException should be thrown and no object returned`() = runBlocking {
        coEvery { personDao.getPerson() } returns null
        coEvery { apiService.getAccountHolder() } returns AccountHolderDto("0123456789", "INDIVIDUAL")
        coEvery { apiService.getAccountHolder() } throws ApiException(errorResponseDto)

        try {
            underTest.getPerson()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(ErrorResponseMapper.toDomain(apiException.errorResponse), e.error)
        }
    }
}