package com.sycosoft.allsee.domain.models

data class FullBalance(
    val acceptedOverdraft: Balance,
    val amount: Balance,
    val clearedBalance: Balance,
    val effectiveBalance: Balance,
    val pendingTransactions: Balance,
    val totalClearedBalance: Balance,
    val totalEffectiveBalance: Balance,
)