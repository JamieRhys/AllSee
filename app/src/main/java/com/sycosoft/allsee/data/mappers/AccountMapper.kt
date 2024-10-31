package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.ApiAccount
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.type.AccountType
import com.sycosoft.allsee.domain.models.type.CurrencyType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeParseException
import java.util.UUID

/** Maps Starling Banks Account to each model type within the app. */
class AccountMapper {
    companion object {
        /** Converts an [ApiAccount] object from Starling Bank's API to an [Account] object.
         *
         * If any UUID's are missing, or malformed, they will be set to 0L.
         *
         * @param apiAccount The [ApiAccount] object to be converted from.
         * @return The converted [Account] object.
         */
        fun fromApiToDomain(apiAccount: ApiAccount): Account = Account(
            // If the UUID is malformed or missing, set it to 0L.
            uuid = try {
                UUID.fromString(apiAccount.accountUid)
            } catch (e: IllegalArgumentException) {
                UUID(0L, 0L)
            },
            type = when (apiAccount.accountType) {
                "PRIMARY" -> AccountType.Primary
                "ADDITIONAL" -> AccountType.Additional
                "LOAN" -> AccountType.Loan
                "FIXED_TERM_DEPOSIT" -> AccountType.FixedTermDeposit
                else -> AccountType.Primary
            },
            defaultCategory = try {
                UUID.fromString(apiAccount.defaultCategory)
            } catch (e: IllegalArgumentException) {
                UUID(0L, 0L)
            },
            currency = try {
                CurrencyType.valueOf(apiAccount.currency.uppercase())
            } catch (e: IllegalArgumentException) {
                CurrencyType.UNDEFINED
            },
            createdAt = try {
                LocalDateTime.ofInstant(
                    Instant.parse(apiAccount.createdAt),
                    ZoneId.systemDefault()
                )
            } catch (e: DateTimeParseException) {
                LocalDateTime.now()
            },
            name = apiAccount.name,
        )

        // TODO: Implement fromDomainToEntity(account: Account): EntityAccount

        // TODO: Implement fromEntityToDomain(entityAccount: EntityAccount): Account
    }
}