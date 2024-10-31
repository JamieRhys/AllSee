package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.ApiAccountHolderName
import junit.framework.TestCase.assertEquals
import org.junit.Test

class AccountHolderNameMapperTest {
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

    private val apiAccountHolderName = ApiAccountHolderName(
        accountHolderName = ValidTestData.VALID_NAME
    )

// endregion
// region From API to Domain

    @Test
    fun whenMappingFromApiToDomain_givenValidApiAccountHolderName_thenAccountHolderNameIsMapped() {
        val accountHolderName = AccountHolderNameMapper.fromApiToDomain(apiAccountHolderName)

        assertEquals(accountHolderName.name, ValidTestData.VALID_NAME)
    }

    @Test
    fun whenMappingFromApiToDomain_givenMissingName_thenNameIsReplacedWithTempName() {
        val invalidApiAccountHolderName = apiAccountHolderName.copy(accountHolderName = InvalidTestData.MISSING_NAME)

        val accountHolderName = AccountHolderNameMapper.fromApiToDomain(invalidApiAccountHolderName)

        assertEquals(accountHolderName.name, "Joe Blogs")
    }

// endregion
}