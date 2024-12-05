package com.sycosoft.allsee.data.local.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sycosoft.allsee.data.local.database.AppDatabase
import com.sycosoft.allsee.data.local.models.PersonEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PersonDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var underTest: PersonDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()

        underTest = database.personDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun whenPersonObjectInserted_thenIdIsReturnedFromDatabase() = runTest {
        val expected = 1L
        val person = PersonEntity(
            id = 0,
            uid = "uid",
            type = "INDIVIDUAL",
            title = "Mr",
            firstName = "Joe",
            lastName = "Bloggs",
            dob = "1975-01-01",
            email = "test@test.com",
            phone = "0123456789"
        )

        val actual = underTest.insertPerson(person)

        assertEquals(expected, actual)
    }

    @Test
    fun whenRequestingPersonWithValidId_thenPersonObjectIsReturned() = runTest {
        val expected = PersonEntity(
            id = 1,
            uid = "uid",
            type = "INDIVIDUAL",
            title = "Mr",
            firstName = "Joe",
            lastName = "Bloggs",
            dob = "1975-01-01",
            email = "test@test.com",
            phone = "0123456789"
        )
        underTest.insertPerson(expected)

        val actual = underTest.getPersonById(expected.id)

        assertEquals(expected, actual)
    }

    @Test
    fun whenRequestingPersonWithInvalidId_thenNullIsReturned() = runTest {
        val expected = null
        val person = PersonEntity(
            id = 1,
            uid = "uid",
            type = "INDIVIDUAL",
            title = "Mr",
            firstName = "Joe",
            lastName = "Bloggs",
            dob = "1975-01-01",
            email = "test@test.com",
            phone = "0123456789"
        )

        underTest.insertPerson(person)

        val actual = underTest.getPersonById(2)

        assertEquals(expected, actual)

    }
}