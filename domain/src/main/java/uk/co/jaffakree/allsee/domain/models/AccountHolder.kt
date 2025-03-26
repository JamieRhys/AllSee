package uk.co.jaffakree.allsee.domain.models

import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import java.util.UUID


data class AccountHolder(
    val uid: UUID,
    val type: AccountHolderType,
)