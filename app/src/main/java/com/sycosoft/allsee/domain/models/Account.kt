package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import java.time.OffsetDateTime
import java.util.UUID

data class Account(
    val accountUid: UUID,
    val accountType: AccountType,
    val defaultCategory: UUID,
    val currency: CurrencyType,
    val createdAt: OffsetDateTime,
    val name: String,
)