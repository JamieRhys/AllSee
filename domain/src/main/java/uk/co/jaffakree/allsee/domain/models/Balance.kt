package uk.co.jaffakree.allsee.domain.models

import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType

data class Balance(
    val currency: CurrencyType,
    val minorUnits: Int,
    val type: BalanceType,
)
