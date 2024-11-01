package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.AccountHolderDto
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.type.AccountHolderType

/** Maps Starling Banks AccountHolder to each model type used in the app. */
object AccountHolderDtoMapper {

    /** Converts an [AccountHolderDto] object from Starling Bank's API to an [AccountHolder] object.
     *
     * @param accountHolderDto The [AccountHolderDto] object to be converted from.
     * @return The converted [Account] object.
     */
    fun mapApiToDomain(accountHolderDto: AccountHolderDto): AccountHolder = AccountHolder(
        uuid = MapperHelpers.mapToUUID(accountHolderDto.accountHolderUid),
        type = mapToAccountHolderType(accountHolderDto.accountHolderType),
    )

    private fun mapToAccountHolderType(type: String) = AccountHolderType.valueOf(type)
}