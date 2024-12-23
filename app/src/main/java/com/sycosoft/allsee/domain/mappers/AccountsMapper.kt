package com.sycosoft.allsee.domain.mappers

import com.sycosoft.allsee.data.local.models.AccountEntity
import com.sycosoft.allsee.data.remote.models.AccountListDto
import com.sycosoft.allsee.domain.models.Account
import com.sycosoft.allsee.domain.models.types.AccountType
import com.sycosoft.allsee.domain.models.types.CurrencyType
import java.time.LocalDateTime
import java.util.UUID

object AccountsMapper {
    fun toDomain(dto: AccountListDto): List<Account> = dto.accounts.map { account ->
        Account(
            accountUid = UUID.fromString(account.accountUid),
            accountType = AccountType.valueOf(account.accountType),
            defaultCategory = UUID.fromString(account.defaultCategory),
            currency = CurrencyType.valueOf(account.currency),
            createdAt = LocalDateTime.parse(account.createdAt),
            name = account.name,
        )
    }

    fun toDomain(entities: List<AccountEntity>): List<Account> = entities.map { entity ->
        Account(
            accountUid = UUID.fromString(entity.uid),
            accountType = AccountType.entries[entity.accountType],
            defaultCategory = UUID.fromString(entity.defaultCategory),
            currency = CurrencyType.entries[entity.currency],
            createdAt = LocalDateTime.parse(entity.createdAt),
            name = entity.name,
        )
    }

    fun toEntity(domain: List<Account>): List<AccountEntity> = domain.map { account ->
        AccountEntity(
            uid =  account.accountUid.toString(),
            defaultCategory =  account.defaultCategory.toString(),
            accountType = account.accountType.ordinal,
            currency =  account.currency.ordinal,
            createdAt = account.createdAt.toString(),
            name = account.name,
        )
    }

    fun toEntity(dto: AccountListDto): List<AccountEntity> = dto.accounts.map { account ->
        AccountEntity(
            uid = account.accountUid,
            defaultCategory = account.defaultCategory,
            accountType = AccountType.valueOf(account.accountType).ordinal,
            currency = CurrencyType.valueOf(account.currency).ordinal,
            createdAt = account.createdAt,
            name = account.name,
        )
    }
}