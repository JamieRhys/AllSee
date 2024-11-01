package com.sycosoft.allsee.domain.models.type

import com.sycosoft.allsee.domain.models.AccountHolder

/**
 * Represents the type of account holder that an [AccountHolder] can be.
 */
enum class AccountHolderType(name: String) {
    INDIVIDUAL("Individual"),
    BUSINESS("Business"),
    SOLE_TRADER("Sole Trader"),
    JOINT("Joint"),
    BANKING_AS_A_SERVICE("Banking as a Service"),
}