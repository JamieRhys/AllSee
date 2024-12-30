package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.remote.models.FullBalanceDto
import com.sycosoft.allsee.domain.models.FullBalance
import javax.inject.Inject

class FullBalanceMapper @Inject constructor() {
    fun toDomain(dto: FullBalanceDto): FullBalance = FullBalance(
        acceptedOverdraft = BalanceMapper().toDomain(dto.acceptedOverdraft),
        amount = BalanceMapper().toDomain(dto.amount),
        clearedBalance = BalanceMapper().toDomain(dto.clearedBalance),
        effectiveBalance = BalanceMapper().toDomain(dto.effectiveBalance),
        pendingTransactions = BalanceMapper().toDomain(dto.pendingTransactions),
        totalClearedBalance = BalanceMapper().toDomain(dto.totalClearedBalance),
        totalEffectiveBalance = BalanceMapper().toDomain(dto.totalEffectiveBalance),
    )
}