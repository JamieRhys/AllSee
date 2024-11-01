package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.AccountHolderDto
import com.sycosoft.allsee.domain.models.type.AccountHolderType
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.UUID

class AccountHolderDtoMapperTest {
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

    private val accountHolderDto = AccountHolderDto(
        accountHolderUid = ValidTestData.VALID_UUID.toString(),
        accountHolderType = ValidTestData.VALID_ACCOUNT_TYPE,
    )

// endregion
// region From API to Domain

    @Test
    fun whenMappingMapApiToDomain_givenValidApiAccountHolder_thenAccountHolderIsMapped() {
        val accountHolder = AccountHolderDtoMapper.mapApiToDomain(accountHolderDto)

        assertEquals(accountHolder.uuid, ValidTestData.VALID_UUID)
        assertEquals(accountHolder.type, AccountHolderType.INDIVIDUAL)
    }

    @Test
    fun whenMappingMapApiToDomain_givenInvalidAccountHolderUid_thenAccountHolderUUIDShouldBe0L() {
        val invalidApiAccountHolder = accountHolderDto.copy(accountHolderUid = InvalidTestData.INVALID_UUID)

        val accountHolder = AccountHolderDtoMapper.mapApiToDomain(invalidApiAccountHolder)

        assertEquals(accountHolder.uuid, UUID(0L, 0L))
    }

    @Test
    fun whenMappingMapApiToDomain_givenMissingAccountHolderUid_thenAccountHolderUUIDShouldBe0L() {
        val invalidApiAccountHolder = accountHolderDto.copy(accountHolderUid = InvalidTestData.MISSING_UUID)

        val accountHolder = AccountHolderDtoMapper.mapApiToDomain(invalidApiAccountHolder)

        assertEquals(accountHolder.uuid, UUID(0L, 0L))
    }

    @Test
    fun whenMappingMapApiToDomain_givenInvalidAccountHolderType_thenAccountHolderTypeIsIndividual() {
        val invalidApiAccountHolder = accountHolderDto.copy(accountHolderType = InvalidTestData.INVALID_ACCOUNT_TYPE)

        val accountHolder = AccountHolderDtoMapper.mapApiToDomain(invalidApiAccountHolder)

        assertEquals(accountHolder.type, AccountHolderType.INDIVIDUAL)
    }

// endregion
}