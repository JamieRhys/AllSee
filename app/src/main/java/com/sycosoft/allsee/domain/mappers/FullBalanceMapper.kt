package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.remote.models.FullBalanceDto
import com.sycosoft.allsee.domain.models.FullBalance
import com.sycosoft.allsee.domain.models.types.BalanceType
import javax.inject.Inject

class FullBalanceMapper @Inject constructor() {
    fun toDomain(dto: FullBalanceDto): FullBalance = FullBalance(
        acceptedOverdraft = BalanceMapper().toDomain(
            dto = dto.acceptedOverdraft,
            type = BalanceType.ACCEPTED_OVERDRAFT,
        ),
        amount = BalanceMapper().toDomain(
            dto = dto.amount,
            type = BalanceType.AMOUNT,
        ),
        clearedBalance = BalanceMapper().toDomain(
            dto = dto.clearedBalance,
            type = BalanceType.CLEARED_BALANCE,
        ),
        effectiveBalance = BalanceMapper().toDomain(
            dto = dto.effectiveBalance,
            type = BalanceType.EFFECTIVE_BALANCE,
        ),
        pendingTransactions = BalanceMapper().toDomain(
            dto = dto.pendingTransactions,
            type = BalanceType.PENDING_TRANSACTIONS,
        ),
        totalClearedBalance = BalanceMapper().toDomain(
            dto = dto.totalClearedBalance,
            type = BalanceType.TOTAL_CLEARED_BALANCE,
        ),
        totalEffectiveBalance = BalanceMapper().toDomain(
            dto = dto.totalEffectiveBalance,
            type = BalanceType.TOTAL_EFFECTIVE_BALANCE,
        ),
    )
}