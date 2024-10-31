package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.ApiAccount
import com.sycosoft.allsee.domain.models.type.AccountType
import com.sycosoft.allsee.domain.models.type.CurrencyType
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

class AccountMapperTest {
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
            const val MISSING_UUID_STRING = ""
            const val INVALID_ACCOUNT_TYPE_STRING = "INVALID"
            const val INVALID_CURRENCY_TYPE_STRING = "INVALID"
            const val INVALID_CREATED_AT_STRING = "INVALID"
        }
    }

    private val apiAccount = ApiAccount(
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
    fun whenMappingFromApiToDomain_givenValidApiAccount_thenAccountIsMapped() {
        val account = AccountMapper.fromApiToDomain(apiAccount)

        assertEquals(account.uuid, ValidTestData.VALID_UUID)
        assertEquals(account.type, AccountType.Primary)
        assertEquals(account.defaultCategory, ValidTestData.VALID_DEFAULT_CATEGORY_UUID)
        assertEquals(account.currency, CurrencyType.GBP)
        assertEquals(account.createdAt, ValidTestData.VALID_CREATED_AT)
        assertEquals(account.name, ValidTestData.VALID_NAME)
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidAccountUid_thenAccountUUIDShouldBe0L() {
        val invalidApiAccount = apiAccount.copy(accountUid = InvalidTestData.INVALID_UUID_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.uuid, UUID(0L, 0L))
    }

    @Test
    fun whenMappingFromApiToDomain_givenMissingAccountUid_thenAccountUUIDShouldBe0L() {
        val invalidApiAccount = apiAccount.copy(accountUid = InvalidTestData.MISSING_UUID_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.uuid, UUID(0L, 0L))
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidAccountType_thenAccountTypeIsPrimary() {
        val invalidApiAccount = apiAccount.copy(accountType = InvalidTestData.INVALID_ACCOUNT_TYPE_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.type, AccountType.Primary)
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidDefaultCategoryUUID_thenDefaultCategoryUUIDIs0L() {
        val invalidApiAccount = apiAccount.copy(defaultCategory = InvalidTestData.INVALID_UUID_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.defaultCategory, UUID(0L, 0L))
    }

    @Test
    fun whenMappingFromApiToDomain_givenMissingDefaultCategoryUUID_thenDefaultCategoryUUIDIs0L() {
        val invalidApiAccount = apiAccount.copy(defaultCategory = InvalidTestData.MISSING_UUID_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.defaultCategory, UUID(0L, 0L))
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidCurrency_thenCurrencyIsUndefined() {
        val invalidApiAccount = apiAccount.copy(currency = InvalidTestData.INVALID_CURRENCY_TYPE_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.currency, CurrencyType.UNDEFINED)
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidCreatedAt_thenCreatedAtIsNow() {
        val invalidApiAccount = apiAccount.copy(createdAt = InvalidTestData.INVALID_CREATED_AT_STRING)

        val account = AccountMapper.fromApiToDomain(invalidApiAccount)

        assertEquals(account.createdAt, LocalDateTime.now())
    }

// endregion
}