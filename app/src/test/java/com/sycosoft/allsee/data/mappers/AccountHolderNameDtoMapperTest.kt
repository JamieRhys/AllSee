package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.AccountHolderNameDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AccountHolderNameDtoMapperTest {
// region Test Data

    private class ValidTestData {
        companion object {
            const val VALID_NAME = "John Smith"
        }
    }

    private class InvalidTestData {
        companion object {
            const val MISSING_NAME = ""
        }
    }

    private val accountHolderNameDto = AccountHolderNameDto(
        accountHolderName = ValidTestData.VALID_NAME
    )

// endregion
// region From API to Domain

    @Test
    fun whenMappingMapApiToDomain_givenValidApiAccountHolderName_thenAccountHolderNameIsMapped() {
        val accountHolderName = AccountHolderNameDtoMapper.mapApiToDomain(accountHolderNameDto)

        assertEquals(accountHolderName.name, ValidTestData.VALID_NAME)
    }

    @Test
    fun whenMappingMapApiToDomain_givenMissingName_thenNameIsReplacedWithTempName() {
        val invalidApiAccountHolderName = accountHolderNameDto.copy(accountHolderName = InvalidTestData.MISSING_NAME)

        val accountHolderName = AccountHolderNameDtoMapper.mapApiToDomain(invalidApiAccountHolderName)

        assertEquals(accountHolderName.name, "Joe Blogs")
    }

// endregion
}