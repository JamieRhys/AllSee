package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.data.local.models.AccountEntity
import uk.co.jaffakree.allsee.domain.models.Account
import uk.co.jaffakree.allsee.domain.models.types.AccountType
import uk.co.jaffakree.allsee.domain.models.types.CurrencyType
import uk.co.jaffakree.allsee.remote.models.AccountDto
import uk.co.jaffakree.allsee.remote.models.AccountIdentifierDto
import java.time.OffsetDateTime
import java.util.UUID

object AccountsMapper {

    fun toDomain(dto: AccountDto, identifier: AccountIdentifierDto): Account = Account(
        accountUid = dto.accountUid,
        accountType = dto.accountType,
        defaultCategory = dto.defaultCategory,
        currency = dto.currency,
        createdAt = dto.createdAt,
        name = dto.name,
        accountIdentifier = identifier.accountIdentifier,
        bankIdentifier = identifier.bankIdentifier,
        iban = identifier.iban,
        bic = identifier.bic,
    )

    fun toDomain(entities: List<AccountEntity>): List<Account> = entities.map { entity ->
        Account(
            accountUid = UUID.fromString(entity.uid),
            accountType = AccountType.entries[entity.accountType],
            defaultCategory = UUID.fromString(entity.defaultCategory),
            currency = CurrencyType.entries[entity.currency],
            createdAt = OffsetDateTime.parse(entity.createdAt),
            name = entity.name,
            accountIdentifier = entity.accountIdentifier,
            bankIdentifier = entity.bankIdentifier,
            iban = entity.iban,
            bic = entity.bic,
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
            accountIdentifier = account.accountIdentifier,
            bankIdentifier = account.bankIdentifier,
            iban = account.iban,
            bic = account.bic,
        )
    }
}