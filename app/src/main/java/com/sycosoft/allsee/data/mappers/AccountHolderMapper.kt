package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import uk.co.jaffakree.allsee.domain.models.AccountHolder
import uk.co.jaffakree.allsee.domain.models.types.AccountHolderType

object AccountHolderMapper {
    fun toDomain(dto: AccountHolderDto): AccountHolder = AccountHolder(
        uid = dto.accountHolderUid,
        type = AccountHolderType.valueOf(dto.accountHolderType)
    )
}