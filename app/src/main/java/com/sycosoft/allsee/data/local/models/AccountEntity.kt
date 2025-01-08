package com.sycosoft.allsee.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType

/** This class represents an account in the database.
 *
 * @property uid The unique identifier for the account provided by the API.
 * @property defaultCategory Used to access the payments and transaction feeds for this account.
 * @property currency The currency type of the account.
 * @property createdAt The date and time the account was created.
 * @property name The name of the account given by the user.
 * @property accountIdentifier The account identifier for the account (also known as account number).
 * @property bankIdentifier The bank identifier for the account (also known as sort code).
 * @property iban The International Bank Account Number for the account.
 * @property bic The Bank Identifier Code for the account.
 *
 * @see AccountType
 * @see CurrencyType
 */
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "account_type") val accountType: Int,
    @ColumnInfo(name = "default_category") val defaultCategory: String,
    @ColumnInfo(name = "currency_type") val currency: Int,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "account_name") val name: String,
    @ColumnInfo(name = "account_identifier") val accountIdentifier: String,
    @ColumnInfo(name = "bank_identifier") val bankIdentifier: String,
    @ColumnInfo(name = "iban") val iban: String,
    @ColumnInfo(name = "bic") val bic: String,
)