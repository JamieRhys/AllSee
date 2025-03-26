package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.data.local.models.BalanceEntity
import uk.co.jaffakree.allsee.domain.models.Balance
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import uk.co.jaffakree.allsee.remote.models.BalanceDto
import java.util.UUID
import javax.inject.Inject

class BalanceMapper @Inject constructor() {
    fun toDomain(dto: BalanceDto, type: BalanceType): Balance = Balance(
        currency = dto.currency,
        type = type,
        minorUnits = dto.minorUnits,
    )

    fun toDomain(entity: BalanceEntity): Balance = Balance(
        currency = CurrencyType.entries[entity.currency],
        type = BalanceType.entries[entity.type],
        minorUnits = entity.minorUnits,
    )

    fun toEntity(domain: Balance, accountUid: UUID): BalanceEntity = BalanceEntity(
        accountUid = accountUid.toString(),
        type = domain.type.ordinal,
        currency = domain.currency.ordinal,
        minorUnits = domain.minorUnits,
    )
}