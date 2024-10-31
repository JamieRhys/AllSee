package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.ApiAccountHolderName
import com.sycosoft.allsee.domain.models.AccountHolderName

/** Maps Starling Banks Account Holder Name to each model type within the app. */
class AccountHolderNameMapper {
    companion object {
        /** Converts an [ApiAccountHolderName] object from Starling Bank's API to an [AccountHolderName] object.
         *
         * If any UUID's are missing, or malformed, they will be set to 0L.
         *
         * @param apiAccountHolderName The [ApiAccountHolderName] object to be converted from.
         * @return The converted [AccountHolderName] object.
         */
        fun fromApiToDomain(apiAccountHolderName: ApiAccountHolderName): AccountHolderName =
            AccountHolderName(
                name = apiAccountHolderName.accountHolderName.ifBlank { "Joe Blogs" },
            )
    }
}