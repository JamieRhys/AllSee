package com.sycosoft.allsee.data.mappers

import com.sycosoft.allsee.data.remote.model.ApiAccountHolder
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.AccountHolder
import com.sycosoft.allsee.domain.models.type.AccountHolderType
import java.util.UUID

/** Maps Starling Banks AccountHolder to each model type used in the app. */
class AccountHolderMapper {
    companion object {
        /** Converts an [ApiAccountHolder] object from Starling Bank's API to an [AccountHolder] object.
         *
         * If any UUID's are missing, or malformed, they will be set to 0L.
         *
         * @param apiAccountHolder The [ApiAccountHolder] object to be converted from.
         * @return The converted [Account] object.
         */
        fun fromApiToDomain(apiAccountHolder: ApiAccountHolder): AccountHolder = AccountHolder(
            uuid = try {
                UUID.fromString(apiAccountHolder.accountHolderUid)
            } catch (e: IllegalArgumentException) {
                UUID(0L, 0L)
            },
            type = when(apiAccountHolder.accountHolderType) {
                "INDIVIDUAL" -> AccountHolderType.Individual
                "BUSINESS" -> AccountHolderType.Business
                "SOLE_TRADER" -> AccountHolderType.SoleTrader
                "BANKING_AS_A_SERVICE" -> AccountHolderType.BankingAsAService
                else -> AccountHolderType.Individual
            },
        )
    }
}