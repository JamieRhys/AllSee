package uk.co.jaffakree.allsee.remote.models

import uk.co.jaffakree.allsee.domain.models.types.CurrencyType

data class BalanceDto(
    val currency: CurrencyType,
    val minorUnits: Int
)