package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.ApiAccountHolder
import com.sycosoft.allsee.domain.models.type.AccountHolderType
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.UUID

class AccountHolderMapperTest {
// region Test Data

    private class ValidTestData {
        companion object {
            val VALID_UUID: UUID = UUID.randomUUID()
            const val VALID_ACCOUNT_TYPE: String = "INDIVIDUAL"
        }
    }

    private class InvalidTestData {
        companion object {
            const val INVALID_UUID: String = "INVALID"
            const val MISSING_UUID: String = ""
            const val INVALID_ACCOUNT_TYPE: String = "INVALID"
        }
    }

    private val apiAccountHolder = ApiAccountHolder(
        accountHolderUid = ValidTestData.VALID_UUID.toString(),
        accountHolderType = ValidTestData.VALID_ACCOUNT_TYPE,
    )

// endregion
// region From API to Domain

    @Test
    fun whenMappingFromApiToDomain_givenValidApiAccountHolder_thenAccountHolderIsMapped() {
        val accountHolder = AccountHolderMapper.fromApiToDomain(apiAccountHolder)

        assertEquals(accountHolder.uuid, ValidTestData.VALID_UUID)
        assertEquals(accountHolder.type, AccountHolderType.Individual)
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidAccountHolderUid_thenAccountHolderUUIDShouldBe0L() {
        val invalidApiAccountHolder = apiAccountHolder.copy(accountHolderUid = InvalidTestData.INVALID_UUID)

        val accountHolder = AccountHolderMapper.fromApiToDomain(invalidApiAccountHolder)

        assertEquals(accountHolder.uuid, UUID(0L, 0L))
    }

    @Test
    fun whenMappingFromApiToDomain_givenMissingAccountHolderUid_thenAccountHolderUUIDShouldBe0L() {
        val invalidApiAccountHolder = apiAccountHolder.copy(accountHolderUid = InvalidTestData.MISSING_UUID)

        val accountHolder = AccountHolderMapper.fromApiToDomain(invalidApiAccountHolder)

        assertEquals(accountHolder.uuid, UUID(0L, 0L))
    }

    @Test
    fun whenMappingFromApiToDomain_givenInvalidAccountHolderType_thenAccountHolderTypeIsIndividual() {
        val invalidApiAccountHolder = apiAccountHolder.copy(accountHolderType = InvalidTestData.INVALID_ACCOUNT_TYPE)

        val accountHolder = AccountHolderMapper.fromApiToDomain(invalidApiAccountHolder)

        assertEquals(accountHolder.type, AccountHolderType.Individual)
    }

// endregion
}