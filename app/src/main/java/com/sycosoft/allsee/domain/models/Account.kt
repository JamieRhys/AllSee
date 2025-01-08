package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import java.time.OffsetDateTime
import java.util.UUID

/** Represents an account for the user.
 *
 * @property accountUid The unique identifier for the account, provided by the API.
 * @property accountType The type of account.
 * @property defaultCategory The default category for the account.
 * @property currency The currency type for the account.
 * @property createdAt The date and time of creation for the account.
 * @property name The name given by the user for the account.
 * @property accountIdentifier The identifier for the account (also known as the account number).
 * @property bankIdentifier The identifier for the bank (also known as the sort code).
 * @property iban The International Bank Account Number for the account.
 * @property bic The Bank Identifier Code for the account.
 *
 * @see [AccountType]
 * @see [CurrencyType]
 */
data class Account(
    val accountUid: UUID,
    val accountType: AccountType,
    val defaultCategory: UUID,
    val currency: CurrencyType,
    val createdAt: OffsetDateTime,
    val name: String,
    val accountIdentifier: String,
    val bankIdentifier: String,
    val iban: String,
    val bic: String,
)