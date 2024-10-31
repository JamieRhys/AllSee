package com.sycosoft.allsee.domain.models.type

import com.sycosoft.allsee.domain.models.Account

/** Represents the account type for the [Account]. */
enum class AccountType(name: String) {
    Primary("Primary"),
    Additional("Additional"),
    Loan("Loan"),
    FixedTermDeposit("Fixed Term Deposit"),
}