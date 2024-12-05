package com.sycosoft.allsee.data.repository

import android.util.Log
import com.sycosoft.allsee.data.local.DatabaseException
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.local.database.dao.PersonDao
import com.sycosoft.allsee.data.local.models.PersonEntity
import com.sycosoft.allsee.domain.mappers.AccountHolderMapper
import com.sycosoft.allsee.domain.mappers.ErrorResponseMapper
import com.sycosoft.allsee.domain.mappers.IdentityMapper
import com.sycosoft.allsee.data.remote.exceptions.ApiException
import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.data.remote.models.ErrorResponseDto
import com.sycosoft.allsee.data.remote.models.IdentityDto
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.exceptions.RepositoryException
import com.sycosoft.allsee.domain.mappers.PersonMapper
import com.sycosoft.allsee.domain.models.ErrorResponse
import com.sycosoft.allsee.domain.models.Person
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class AppRepositoryImplTest {
    private val apiService: StarlingBankApiService = mockk(relaxed = true)
    private val tokenProvider: TokenProvider = mockk(relaxed = true)
    private val personDao: PersonDao = mockk(relaxed = true)
    private val identityMapper: IdentityMapper = IdentityMapper()
    private val personMapper: PersonMapper = PersonMapper()
    private lateinit var underTest: AppRepositoryImpl

    private val errorResponseDto = ErrorResponseDto("error", "Error Description")
    private val apiException = ApiException(errorResponseDto)

    @Before
    fun setUp() {
        underTest = AppRepositoryImpl(
            apiService = apiService,
            personDao = personDao,
            tokenProvider = tokenProvider,
            identityMapper = identityMapper,
            personMapper = personMapper,
        )

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

    // Get Account Holder
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

    // Get Identity
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

    // Get Person
    @Test
    fun `When Database returns valid object, Then database person is returned`() = runBlocking {
        val accountHolderDto = AccountHolderDto("012456789", "INDIVIDUAL")
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
        val accountHolderDto = AccountHolderDto("012456789", "INDIVIDUAL")
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