package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.local.models.AccountEntity
import com.sycosoft.allsee.data.remote.models.AccountListDto
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import java.util.UUID

object AccountsMapper {
    fun toDomain(dto: AccountListDto): List<Account> = dto.accounts.map { account ->
        Account(
            accountUid = UUID.fromString(account.accountUid),
            accountType = AccountType.valueOf(account.accountType),
            defaultCategory = UUID.fromString(account.defaultCategory),
            currency = CurrencyType.valueOf(account.currency),
            createdAt = account.createdAt,
            name = account.name,
        )
    }

    fun toEntity(domain: List<Account>): List<AccountEntity> = domain.map { account ->
        AccountEntity(
            uid =  account.accountUid.toString(),
            defaultCategory =  account.defaultCategory.toString(),
            accountType = account.accountType.ordinal,
            currency =  account.currency.ordinal,
            createdAt = account.createdAt,
            name = account.name,
        )
    }
}