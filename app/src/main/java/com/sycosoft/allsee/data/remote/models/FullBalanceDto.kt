package com.sycosoft.allsee.data.remote.models

data class FullBalanceDto(
    val acceptedOverdraft: BalanceDto,
    val amount: BalanceDto,
    val clearedBalance: BalanceDto,
    val effectiveBalance: BalanceDto,
    val pendingTransactions: BalanceDto,
    val totalClearedBalance: BalanceDto,
    val totalEffectiveBalance: BalanceDto,
)