package uk.co.jaffakree.allsee.mappers

import uk.co.jaffakree.allsee.domain.models.FeedItem
import uk.co.jaffakree.allsee.domain.models.types.BalanceType
import uk.co.jaffakree.allsee.domain.models.types.CountryType
import uk.co.jaffakree.allsee.domain.models.types.FeedCounterPartyType
import uk.co.jaffakree.allsee.domain.models.types.FeedDirectionType
import uk.co.jaffakree.allsee.domain.models.types.FeedSourceSubType
import uk.co.jaffakree.allsee.domain.models.types.FeedSourceType
import uk.co.jaffakree.allsee.domain.models.types.FeedSpendingCategory
import uk.co.jaffakree.allsee.remote.models.FeedItemDto
import uk.co.jaffakree.allsee.remote.models.FeedItemsDto
import java.time.OffsetDateTime
import java.util.UUID
import javax.inject.Inject

class FeedItemMapper @Inject constructor(
    private val balanceMapper: BalanceMapper,
) {
    fun toDomain(item: FeedItemDto): FeedItem = FeedItem(
        feedItemUid = UUID.fromString(item.feedItemUid),
        amount = balanceMapper.toDomain(item.amount, BalanceType.AMOUNT),
        transactionTime = OffsetDateTime.parse(item.transactionTime),
        settlementTime = OffsetDateTime.parse(item.settlementTime),
        direction = FeedDirectionType.valueOf(item.direction),
        source = FeedSourceType.valueOf(item.source),
        sourceSubType = when (item.sourceSubType.isNullOrBlank()) {
            true -> FeedSourceSubType.UNKNOWN
            false -> FeedSourceSubType.valueOf(item.sourceSubType)
        },
        counterPartyType = FeedCounterPartyType.valueOf(item.counterPartyType),
        counterPartyName = item.counterPartyName,
        counterPartySubEntityName = item.counterPartySubEntityName,
        counterPartySubEntityIdentifier = item.counterPartySubEntityIdentifier ?: "",
        exchangeRate = item.exchangeRate,
        totalFees = item.totalFees,
        totalFeeAmount = if (item.totalFeeAmount != null)
            balanceMapper.toDomain(item.totalFeeAmount, BalanceType.AMOUNT)
        else null,
        reference = item.reference,
        country = CountryType.valueOf(item.country),
        spendingCategory = when (item.spendingCategory.isNullOrBlank()) {
            true -> FeedSpendingCategory.OTHER
            false -> FeedSpendingCategory.valueOf(item.spendingCategory)
        },
        userNote = item.userNote,
        hasReceipt = item.hasReceipt,
    )

    fun toDomain(items: FeedItemsDto): List<FeedItem> = items.feedItems.map { toDomain(it) }
}