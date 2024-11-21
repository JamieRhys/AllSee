package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.types.AccountHolderType


data class AccountHolder(
    val uid: String,
    val type: AccountHolderType,
)