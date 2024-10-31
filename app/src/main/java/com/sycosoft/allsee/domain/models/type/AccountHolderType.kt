package com.sycosoft.allsee.domain.models.type

import com.sycosoft.allsee.domain.models.AccountHolder

/**
 * Represents the type of account holder that an [AccountHolder] can be.
 */
enum class AccountHolderType(name: String) {
    Individual("Individual"),
    Business("Business"),
    SoleTrader("Sole Trader"),
    Joint("Joint"),
    BankingAsAService("Banking as a Service"),
}