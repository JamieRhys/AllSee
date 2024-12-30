package com.sycosoft.allsee.presentation.usecases

class FormatBalanceUseCase {
    operator fun invoke(minorUnits: Int): String {
        val wholeUnits = minorUnits / 100
        val decimalUnits = minorUnits % 100
        return "£$wholeUnits.${decimalUnits.toString().padStart(2, '0')}"
    }
}