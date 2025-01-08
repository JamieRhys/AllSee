package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sycosoft.allsee.data.local.database.AppDatabase
import com.sycosoft.allsee.data.local.models.AccountEntity
import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class AccountDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var underTest: AccountsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()

        underTest = database.accountsDao
    }

    @After
    fun teardown() {
        database.close()
    }

    // =============================================================================================
    // == Insert Accounts                                                                         ==
    // =============================================================================================

    @Test
    fun whenAccountsListInserted_ThenListOfIDsReturnedFromDatabase() = runTest {
        val expectedAccount1Uid = UUID.randomUUID().toString()
        val expectedAccount2Uid = UUID.randomUUID().toString()
        val expectedAccount3Uid = UUID.randomUUID().toString()
        val expected = listOf(1L, 2L, 3L)
        val accounts: List<AccountEntity> = listOf(
            AccountEntity(
                uid = expectedAccount1Uid,
                accountType = AccountType.PRIMARY.ordinal,
                defaultCategory = UUID.randomUUID().toString(),
                currency = CurrencyType.GBP.ordinal,
                createdAt = LocalDateTime.now().toString(),
                name = "Personal",
                accountIdentifier = "12345678",
                bankIdentifier = "123456",
                iban = "GB01234567890",
                bic = "30456VHG"
            ),
            AccountEntity(
                uid = expectedAccount2Uid,
                accountType = AccountType.PRIMARY.ordinal,
                defaultCategory = UUID.randomUUID().toString(),
                currency = CurrencyType.GBP.ordinal,
                createdAt = LocalDateTime.now().toString(),
                name = "Personal",
                accountIdentifier = "12345678",
                bankIdentifier = "123456",
                iban = "GB01234567890",
                bic = "30456VHG"
            ),
            AccountEntity(
                uid = expectedAccount3Uid,
                accountType = AccountType.PRIMARY.ordinal,
                defaultCategory = UUID.randomUUID().toString(),
                currency = CurrencyType.GBP.ordinal,
                createdAt = LocalDateTime.now().toString(),
                name = "Personal",
                accountIdentifier = "12345678",
                bankIdentifier = "123456",
                iban = "GB01234567890",
                bic = "30456VHG"
            ),
        )

        val actual = underTest.insertAccounts(accounts)

        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }

    // =============================================================================================
    // == Get all Accounts                                                                        ==
    // =============================================================================================

    @Test
    fun whenRequestingAccounts_ThenListOfAccountsReturned() = runTest {
        val expectedAccount1Uid = UUID.randomUUID().toString()
        val expectedAccount2Uid = UUID.randomUUID().toString()
        val expectedAccount3Uid = UUID.randomUUID().toString()

        val expected: List<AccountEntity> = listOf(
            AccountEntity(
                uid = expectedAccount1Uid,
                accountType = AccountType.PRIMARY.ordinal,
                defaultCategory = UUID.randomUUID().toString(),
                currency = CurrencyType.GBP.ordinal,
                createdAt = LocalDateTime.now().toString(),
                name = "Personal",
                accountIdentifier = "12345678",
                bankIdentifier = "123456",
                iban = "GB01234567890",
                bic = "30456VHG"
            ),
            AccountEntity(
                uid = expectedAccount2Uid,
                accountType = AccountType.PRIMARY.ordinal,
                defaultCategory = UUID.randomUUID().toString(),
                currency = CurrencyType.GBP.ordinal,
                createdAt = LocalDateTime.now().toString(),
                name = "Personal",
                accountIdentifier = "12345678",
                bankIdentifier = "123456",
                iban = "GB01234567890",
                bic = "30456VHG"
            ),
            AccountEntity(
                uid = expectedAccount3Uid,
                accountType = AccountType.PRIMARY.ordinal,
                defaultCategory = UUID.randomUUID().toString(),
                currency = CurrencyType.GBP.ordinal,
                createdAt = LocalDateTime.now().toString(),
                name = "Personal",
                accountIdentifier = "12345678",
                bankIdentifier = "123456",
                iban = "GB01234567890",
                bic = "30456VHG"
            ),
        )

        underTest.insertAccounts(expected)

        val actual = underTest.getAccounts()

        assertEquals(expected.size, actual.size)
        assertEquals(expected, actual)
    }
}