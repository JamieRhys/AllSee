package uk.co.jaffakree.allsee.data.repository

import android.database.sqlite.SQLiteException
import android.util.Log
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
import uk.co.jaffakree.allsee.data.local.DatabaseException
import uk.co.jaffakree.allsee.data.local.TokenProvider
import uk.co.jaffakree.allsee.data.local.database.dao.AccountsDao
import uk.co.jaffakree.allsee.data.local.database.dao.BalanceDao
import uk.co.jaffakree.allsee.data.local.database.dao.PersonDao
import uk.co.jaffakree.allsee.data.local.models.AccountEntity
import uk.co.jaffakree.allsee.data.local.models.BalanceEntity
import uk.co.jaffakree.allsee.data.local.models.PersonEntity
import uk.co.jaffakree.allsee.domain.exceptions.RepositoryException
import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.Balance
import uk.co.jaffakree.allsee.domain.models.ErrorResponse
import uk.co.jaffakree.allsee.domain.models.FullBalance
import uk.co.jaffakree.allsee.domain.models.Person
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import uk.co.jaffakree.allsee.domain.models.types.AccountType
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import uk.co.jaffakree.allsee.domain.models.types.CountryType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import uk.co.jaffakree.allsee.domain.models.types.FeedCounterPartyType
import uk.co.jaffakree.allsee.domain.models.types.FeedSourceSubType
import uk.co.jaffakree.allsee.domain.models.types.FeedSourceType
import uk.co.jaffakree.allsee.domain.models.types.FeedSpendingCategory
import uk.co.jaffakree.allsee.domain.models.types.FeedStatusType
import uk.co.jaffakree.allsee.mappers.AccountHolderMapper
import uk.co.jaffakree.allsee.mappers.AccountsMapper
import uk.co.jaffakree.allsee.mappers.BalanceMapper
import uk.co.jaffakree.allsee.mappers.ErrorResponseMapper
import uk.co.jaffakree.allsee.mappers.FeedItemMapper
import uk.co.jaffakree.allsee.mappers.FullBalanceMapper
import uk.co.jaffakree.allsee.mappers.IdentityMapper
import uk.co.jaffakree.allsee.mappers.PersonMapper
import uk.co.jaffakree.allsee.remote.exceptions.ApiException
import uk.co.jaffakree.allsee.remote.models.AccountDto
import uk.co.jaffakree.allsee.remote.models.AccountHolderDto
import uk.co.jaffakree.allsee.remote.models.AccountIdentifierDto
import uk.co.jaffakree.allsee.remote.models.AccountListDto
import uk.co.jaffakree.allsee.remote.models.BalanceDto
import uk.co.jaffakree.allsee.remote.models.ErrorResponseDto
import uk.co.jaffakree.allsee.remote.models.FeedItemDto
import uk.co.jaffakree.allsee.remote.models.FeedItemsDto
import uk.co.jaffakree.allsee.remote.models.FullBalanceDto
import uk.co.jaffakree.allsee.remote.models.IdentityDto
import uk.co.jaffakree.allsee.remote.services.StarlingBankApiService
import uk.co.jaffakree.allsee.repository.AppRepositoryImpl
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

class AppRepositoryImplTest {
// region Setup and Teardown
    private val apiService: StarlingBankApiService = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)
    private val personDao: PersonDao = mockk(relaxed = true)
    private val accountsDao: AccountsDao = mockk(relaxed = true)
    private val balanceDao: BalanceDao = mockk(relaxed = true)
    private val identityMapper: IdentityMapper = IdentityMapper()
    private val balanceMapper: BalanceMapper = BalanceMapper()
    private val fullBalanceMapper: FullBalanceMapper = FullBalanceMapper()
    private val feedItemMapper: FeedItemMapper = FeedItemMapper(balanceMapper)
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
    private val validFullBalance = FullBalance(
        acceptedOverdraft = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.ACCEPTED_OVERDRAFT,
        ),
        amount = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.AMOUNT,
        ),
        clearedBalance = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.CLEARED_BALANCE,
        ),
        effectiveBalance = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.EFFECTIVE_BALANCE,
        ),
        pendingTransactions = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.PENDING_TRANSACTIONS,
        ),
        totalClearedBalance = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.TOTAL_CLEARED_BALANCE,
        ),
        totalEffectiveBalance = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.TOTAL_EFFECTIVE_BALANCE,
        )
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
            fullBalanceMapper = fullBalanceMapper,
            feedItemMapper = feedItemMapper,
        )

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
    }

// endregion
// region Save Accounts

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

// endregion
// region Save Full Balance

    @Test
    fun `When saving full balance, Then objects should be saved and row returned`() = runTest {
        val fullBalance = validFullBalance.copy()
        val accountUid = UUID.randomUUID()

        val expected = listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L)

        coEvery { balanceDao.insertBalance(any()) } returns expected

        val actual = underTest.saveFullBalance(fullBalance, accountUid)

        coVerify(exactly = 1) { balanceDao.insertBalance(any()) }
        assertEquals(expected, actual)
    }

// endregion
// region Save Person

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

// endregion
// region Save Token

    @Test
    fun `When saveToken is called, Then it should delegate saving token to TokenProvider`() = runBlocking {
        // When and Then
        val token = "dummy_token"
        underTest.saveToken(token)

        // Verify
        coVerify { tokenProvider.saveToken(token) }
    }

// endregion
// region Get Accounts

    @Test
    fun `When API succeeds, Then accounts are returned`() = runTest {
        val apiAccountModel = AccountDto(
            accountUid = UUID.randomUUID(),
            accountType = AccountType.PRIMARY,
            defaultCategory = UUID.randomUUID(),
            currency = CurrencyType.GBP,
            createdAt = OffsetDateTime.now(),
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
    fun `When database returns valid objects, Then database objects should be returned`() = runTest {
        val accountEntity = AccountEntity(
            uid = UUID.randomUUID().toString(),
            accountType = AccountType.PRIMARY.ordinal,
            defaultCategory = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            createdAt = OffsetDateTime.now().toString(),
            name = "Personal",
            accountIdentifier = "12345678",
            bankIdentifier = "123456",
            iban = "GB2L12345678123456",
            bic = "132456"
        )
        val expected = AccountsMapper.toDomain(listOf(accountEntity))

        coEvery { accountsDao.getAccounts() } returns listOf(accountEntity)

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

// endregion
// region Get Account Holder

    @Test
    fun `When API succeeds, Then account holder is returned`() = runBlocking {
        val apiModel = AccountHolderDto(UUID.randomUUID(), "INDIVIDUAL")
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

// endregion
// region Get Balance

    @Test
    fun `When getting balance type accepted overdraft, Then accepted overdraft balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.ACCEPTED_OVERDRAFT,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.ACCEPTED_OVERDRAFT.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.ACCEPTED_OVERDRAFT)

        assertEquals(expected, actual)
    }

    @Test
    fun `When getting balance type amount, Then amount balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.AMOUNT,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.AMOUNT.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.AMOUNT)

        assertEquals(expected, actual)
    }

    @Test
    fun `When getting balance type cleared balance, Then cleared balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.CLEARED_BALANCE,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.CLEARED_BALANCE.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.CLEARED_BALANCE)

        assertEquals(expected, actual)
    }

    @Test
    fun `When getting balance type effective balance, Then effective balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.EFFECTIVE_BALANCE,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.EFFECTIVE_BALANCE.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.EFFECTIVE_BALANCE)

        assertEquals(expected, actual)
    }

    @Test
    fun `When getting balance type total cleared balance, Then total cleared balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.TOTAL_CLEARED_BALANCE,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.TOTAL_CLEARED_BALANCE.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.TOTAL_CLEARED_BALANCE)

        assertEquals(expected, actual)
    }

    @Test
    fun `When getting balance type total effective balance, Then total effective balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.TOTAL_EFFECTIVE_BALANCE,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.TOTAL_EFFECTIVE_BALANCE.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.TOTAL_EFFECTIVE_BALANCE)

        assertEquals(expected, actual)
    }

    @Test
    fun `When getting balance type pending transactions balance, Then pending transactions balance should be returned`() = runTest {
        val expected = Balance(
            currency = CurrencyType.GBP,
            minorUnits = 100,
            type = BalanceType.PENDING_TRANSACTIONS,
        )

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns BalanceEntity(
            id = 0,
            type = BalanceType.PENDING_TRANSACTIONS.ordinal,
            accountUid = UUID.randomUUID().toString(),
            currency = CurrencyType.GBP.ordinal,
            minorUnits = 100,
        )

        val actual = underTest.getBalance(BalanceType.PENDING_TRANSACTIONS)

        assertEquals(expected, actual)
    }

// endregion
// region Get Full Balance

    @Test
    fun `When database is empty, Then full balance should be returned from API`() = runTest {
        val apiModel = FullBalanceDto(
            acceptedOverdraft = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
            amount = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
            clearedBalance = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
            effectiveBalance = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
            pendingTransactions = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
            totalClearedBalance = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
            totalEffectiveBalance = BalanceDto(
                currency = CurrencyType.GBP,
                minorUnits = 100
            ),
        )
        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { balanceDao.getBalanceFromType(any(), any()) } returns null
        coEvery { apiService.getFullBalance(any()) } returns apiModel

        val expected = FullBalanceMapper().toDomain(apiModel)
        val actual = underTest.getFullBalance()

        assertEquals(expected, actual)
    }

// endregion
// region Get Identity
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

// endregion
// region Get Person

    @Test
    fun `When Database returns valid object, Then database person is returned`() = runBlocking {
        val accountHolderDto = AccountHolderDto(UUID.randomUUID(), "INDIVIDUAL")
        val identityDto = IdentityDto("Mr", "John", "Doe", "1975-01-01", "joe.bloggs@example.com", "0123456789")
        val expected = personMapper.toDomain(
            PersonEntity(
                uid = accountHolderDto.accountHolderUid.toString(),
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
    fun `When ApiException thrown during saving of person, Then RepositoryException should be returned and no person object returned`() = runTest {
        val expected = RepositoryException(ErrorResponse("error", "Error Response"))
        coEvery { personDao.getPerson() } returns null
        coEvery { apiService.getAccountHolder() } throws ApiException(ErrorResponseDto("error", "Error Response"))
        coEvery { apiService.getIdentity() } throws ApiException(ErrorResponseDto("error", "Error Response"))

        try {
            underTest.getPerson()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(expected.error, e.error)
        }
    }

    @Test
    fun `When API succeeds, Then person object is returned`() = runBlocking {
        val accountHolderDto = AccountHolderDto(UUID.randomUUID(), "INDIVIDUAL")
        val identityDto = IdentityDto("Mr", "John", "Doe", "1975-01-01", "joe.bloggs@example.com", "0123456789")
        val identity = identityMapper.toDomain(identityDto)
        val accountHolder = AccountHolderMapper.toDomain(accountHolderDto)

        coEvery { personDao.getPerson() } returns null
        coEvery { apiService.getAccountHolder() } returns accountHolderDto
        coEvery { apiService.getIdentity() } returns identityDto

        val actual = underTest.getPerson()
        val expected = Person(
            uid = accountHolder.uid,
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
        coEvery { apiService.getAccountHolder() } returns AccountHolderDto(UUID.randomUUID(), "INDIVIDUAL")
        coEvery { apiService.getAccountHolder() } throws ApiException(errorResponseDto)

        try {
            underTest.getPerson()
            fail("Expected RepositoryException to be thrown")
        } catch(e: RepositoryException) {
            assertEquals(ErrorResponseMapper.toDomain(apiException.errorResponse), e.error)
        }
    }

// endregion
// region Get Recent Feed

    @Test
    fun `When getting recent feed, Then feed should be returned from API`() = runTest {
        val account = validAccount.copy()
        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(account))

        val apiFeed = FeedItemsDto(listOf(
            FeedItemDto(
                feedItemUid = UUID.randomUUID(),
                categoryUid = UUID.randomUUID(),
                amount = BalanceDto(CurrencyType.GBP, 100),
                sourceAmount = null,
                direction = "OUT",
                updatedAt = null,
                transactionTime = OffsetDateTime.now().toString(),
                settlementTime = OffsetDateTime.now().toString(),
                retryAllocationUntilTime = null,
                source = FeedSourceType.MASTER_CARD.name,
                sourceSubType = FeedSourceSubType.CHIP_AND_PIN.name,
                status = FeedStatusType.PENDING.name,
                transactingApplicationUserUid = UUID.randomUUID(),
                counterPartyType = FeedCounterPartyType.MERCHANT.name,
                counterPartyUid = UUID.randomUUID(),
                counterPartyName = "Test Merchant",
                counterPartySubEntityUid = UUID.randomUUID(),
                counterPartySubEntityName = "Test Sub Entity",
                counterPartySubEntityIdentifier = "Test Identifier",
                counterPartySubEntitySubIdentifier = "Test Sub Identifier",
                exchangeRate = null,
                totalFees = null,
                totalFeeAmount = null,
                reference = "Test Reference",
                country = CountryType.GB.name,
                spendingCategory = FeedSpendingCategory.OTHER.name,
                userNote = null,
                roundUp = null,
                hasAttachment = false,
                hasReceipt = false,
                batchPaymentDetails = null
            )
        ))

        coEvery { accountsDao.getAccounts() } returns AccountsMapper.toEntity(listOf(validAccount.copy()))
        coEvery { apiService.getTransactionFeed(any(), any(), any()) } returns apiFeed

        val expected = feedItemMapper.toDomain(apiFeed)
        val actual = underTest.getRecentFeed()

        assertEquals(expected, actual)
    }

// endregion
}