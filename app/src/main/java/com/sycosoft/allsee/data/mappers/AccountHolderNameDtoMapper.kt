package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.AccountHolderNameDto
import com.sycosoft.allsee.domain.models.AccountHolderName

/** Maps Starling Banks Account Holder Name to each model type within the app. */
object AccountHolderNameDtoMapper {
    /** Converts an [AccountHolderNameDto] object from Starling Bank's API to an [AccountHolderName] object.
     *

     *
     * @param accountHolderNameDto The [AccountHolderNameDto] object to be converted from.
     * @return The converted [AccountHolderName] object.
     */
    fun mapApiToDomain(accountHolderNameDto: AccountHolderNameDto): AccountHolderName =
        AccountHolderName(
            name = accountHolderNameDto.accountHolderName,
        )
}