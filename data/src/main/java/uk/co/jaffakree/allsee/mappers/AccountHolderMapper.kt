package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.domain.models.AccountHolder
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType
import uk.co.jaffakree.allsee.remote.models.AccountHolderDto

object AccountHolderMapper {
    fun toDomain(dto: AccountHolderDto): AccountHolder = AccountHolder(
        uid = dto.accountHolderUid,
        type = AccountHolderType.valueOf(dto.accountHolderType)
    )
}