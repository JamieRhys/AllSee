package com.sycosoft.allsee.domain.models.type

import com.sycosoft.allsee.domain.models.Account

/** Represents the account type for the [Account]. */
enum class AccountType(name: String) {
    PRIMARY("Primary"),
    ADDITIONAL("Additional"),
    LOAN("Loan"),
    FIXED_TERM_LOAN("Fixed Term Deposit"),
}