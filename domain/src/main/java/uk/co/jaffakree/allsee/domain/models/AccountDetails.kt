package uk.co.jaffakree.allsee.domain.models

data class AccountDetails(
    val name: String,
    val type: String,
    val accountNumber: String,
    val sortCode: String,
    val iban: String,
    val bic: String,
)