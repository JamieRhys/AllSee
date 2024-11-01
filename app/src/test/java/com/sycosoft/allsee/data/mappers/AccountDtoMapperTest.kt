package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.AccountDto
import com.sycosoft.allsee.domain.models.type.AccountType
import com.sycosoft.allsee.domain.models.type.CurrencyType
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeParseException
import java.util.UUID

class AccountDtoMapperTest {
// region Test Data

    private class ValidTestData {
        companion object {
            const val CREATED_AT_STRING = "2024-10-25T10:35:46.520Z"
            val VALID_UUID: UUID = UUID.randomUUID()
            val VALID_DEFAULT_CATEGORY_UUID: UUID = UUID.randomUUID()
            val VALID_CREATED_AT: LocalDateTime = LocalDateTime.ofInstant(Instant.parse(CREATED_AT_STRING), ZoneId.systemDefault())
            const val VALID_ACCOUNT_TYPE_STRING = "PRIMARY"
            const val VALID_CURRENCY_TYPE_STRING = "GBP"
            const val VALID_NAME = "Personal"
        }
    }

    private class InvalidTestData {
        companion object {
            const val INVALID_UUID_STRING = "INVALID"
            const val MISSING_STRING = ""
            const val INVALID_ACCOUNT_TYPE_STRING = "INVALID"
            const val INVALID_CURRENCY_TYPE_STRING = "INVALID"
            const val INVALID_CREATED_AT_STRING = "INVALID"
            const val INVALID_UPPER_EXCEEDED_DATE_STRING = "1000000000-01-01T00:00:00"
            const val INVALID_LOWER_EXCEEDED_DATE_STRING = "-9999-01-01T00:00:00"
        }
    }

    private val accountDto = AccountDto(
        accountUid = ValidTestData.VALID_UUID.toString(),
        accountType = ValidTestData.VALID_ACCOUNT_TYPE_STRING,
        defaultCategory = ValidTestData.VALID_DEFAULT_CATEGORY_UUID.toString(),
        currency = ValidTestData.VALID_CURRENCY_TYPE_STRING,
        createdAt = ValidTestData.CREATED_AT_STRING,
        name = ValidTestData.VALID_NAME,
    )

// endregion
// region From API to Domain

    @Test
    fun whenMappingMapApiToDomain_givenValidApiAccount_thenAccountIsMapped() {
        val account = AccountDtoMapper.mapApiToDomain(accountDto)

        assertEquals(account.uuid, ValidTestData.VALID_UUID)
        assertEquals(account.type, AccountType.PRIMARY)
        assertEquals(account.defaultCategory, ValidTestData.VALID_DEFAULT_CATEGORY_UUID)
        assertEquals(account.currency, CurrencyType.GBP)
        assertEquals(account.createdAt, ValidTestData.VALID_CREATED_AT)
        assertEquals(account.name, ValidTestData.VALID_NAME)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenInvalidUUID_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(accountUid = InvalidTestData.INVALID_UUID_STRING))
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenMissingUUID_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(accountUid = InvalidTestData.MISSING_STRING))
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenInvalidDefaultCategory_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(defaultCategory = InvalidTestData.INVALID_UUID_STRING))
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenMissingDefaultCategory_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(defaultCategory = InvalidTestData.MISSING_STRING))
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenInvalidAccountType_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(accountType = InvalidTestData.INVALID_ACCOUNT_TYPE_STRING))
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenInvalidCurrency_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(currency = InvalidTestData.INVALID_CURRENCY_TYPE_STRING))
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenMappingApiToDomain_givenMissingCurrency_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(currency = InvalidTestData.MISSING_STRING))
    }

    @Test(expected = DateTimeParseException::class)
    fun whenMappingApiToDomain_givenInvalidCreatedAt_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(createdAt = InvalidTestData.INVALID_CREATED_AT_STRING))
    }

    @Test(expected = DateTimeParseException::class)
    fun whenMappingApiToDomain_givenMissingCreatedAt_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(createdAt = InvalidTestData.MISSING_STRING))
    }

    @Test(expected = DateTimeException::class)
    fun whenMappingApiToDomain_givenUpperExceededDateRange_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(createdAt = InvalidTestData.INVALID_UPPER_EXCEEDED_DATE_STRING))
    }

    @Test(expected = DateTimeException::class)
    fun whenMappingApiToDomain_givenLowerExceededDateRange_thenExceptionIsThrown() {
        AccountDtoMapper.mapApiToDomain(accountDto.copy(createdAt = InvalidTestData.INVALID_LOWER_EXCEEDED_DATE_STRING))
    }

// endregion
}