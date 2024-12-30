package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.types.BalanceType
import com.sycosoft.allsee.domain.models.types.CurrencyType

data class Balance(
    val currency: CurrencyType,
    val minorUnits: Int,
    val type: BalanceType,
)
