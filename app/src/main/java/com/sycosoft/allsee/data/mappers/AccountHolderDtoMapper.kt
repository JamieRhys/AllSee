package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.models.AccountHolderDto
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.types.AccountHolderType

object AccountHolderDtoMapper {
    fun toDomain(dto: AccountHolderDto): AccountHolder = AccountHolder(
        uid = dto.accountHolderUid,
        type = AccountHolderType.valueOf(dto.accountHolderType)
    )
}