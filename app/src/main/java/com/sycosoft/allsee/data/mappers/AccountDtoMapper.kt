package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.AccountDto
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.type.AccountType
import com.sycosoft.allsee.domain.models.type.CurrencyType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

/** Maps Starling Banks Account to each model type within the app. */
object AccountDtoMapper {

    /** Converts an [AccountDto] object from Starling Bank's API to an [Account] object.
     *
     * @param accountDto The [AccountDto] object to be converted from.
     * @return The converted [Account] object.
     */
    fun mapApiToDomain(accountDto: AccountDto): Account = Account(
        // If the UUID is malformed or missing, set it to 0L.
        uuid = MapperHelpers.mapToUUID(accountDto.accountUid),
        type = mapToAccountType(accountDto.accountType),
        defaultCategory = MapperHelpers.mapToUUID(accountDto.defaultCategory),
        currency = mapToCurrencyType(accountDto.currency),
        createdAt = MapperHelpers.mapToLocalDateTime(accountDto.createdAt),
        name = accountDto.name,
    )

    private fun mapToAccountType(accountType: String) = AccountType.valueOf(accountType)

    private fun mapToCurrencyType(currency: String) = CurrencyType.valueOf(currency)
}