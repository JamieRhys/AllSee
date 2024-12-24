package com.sycosoft.allsee.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sycosoft.allsee.domain.models.types.AccountHolderType

/** This class represents a Person in the database.
 *
 * @property uid The unique identifier for this person. This is provided by the API
 * @property type The account holder type.
 * @property title The title for the person.
 * @property firstName The first name of the person.
 * @property lastName The last name of the person.
 * @property dob The date of birth of the person.
 * @property email The email of the person.
 * @property phone The mobile phone number of the person.
 *
 * @see AccountHolderType
 */
@Entity(tableName = "person")
data class PersonEntity(
    @PrimaryKey @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "type")val type: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "dob") val dob: String,
    @ColumnInfo(name = "email")val email: String,
    @ColumnInfo(name = "phone") val phone: String,
)