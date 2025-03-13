package uk.co.jaffakree.allsee.domain.models

import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType


data class AccountHolder(
    val uid: String,
    val type: AccountHolderType,
)