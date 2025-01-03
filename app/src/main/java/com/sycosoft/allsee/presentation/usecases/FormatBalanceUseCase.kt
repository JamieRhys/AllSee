package com.sycosoft.allsee.presentation.usecases

import kotlin.math.abs

class FormatBalanceUseCase {
    operator fun invoke(minorUnits: Int): String {
        val isNegative = minorUnits < 0
        val absoluteBalance = abs(minorUnits)

        val wholeUnits = absoluteBalance / 100
        val decimalUnits = absoluteBalance % 100

        val formattedBalance = "£$wholeUnits.${decimalUnits.toString().padStart(2, '0')}"

        return if (isNegative) "-$formattedBalance" else formattedBalance
    }
}