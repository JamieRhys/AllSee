package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.remote.models.BalanceDto
import com.sycosoft.allsee.domain.models.Balance
import javax.inject.Inject

class BalanceMapper @Inject constructor() {
    fun toDomain(dto: BalanceDto): Balance = Balance(
        currency = dto.currency,
        minorUnits = dto.minorUnits,
    )
}