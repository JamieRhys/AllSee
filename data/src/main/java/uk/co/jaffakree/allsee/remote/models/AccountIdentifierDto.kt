package uk.co.jaffakree.allsee.remote.models

data class AccountIdentifierDto(
    val accountIdentifier: String,
    val accountIdentifiers: List<AccountIdentifier>,
    val bankIdentifier: String,
    val bic: String,
    val iban: String
)

data class AccountIdentifier(
    val accountIdentifier: String,
    val bankIdentifier: String,
    val identifierType: String
)