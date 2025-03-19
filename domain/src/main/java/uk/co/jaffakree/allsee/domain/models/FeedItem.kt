package uk.co.jaffakree.allsee.domain.models

import uk.co.jaffakree.allsee.domain.models.types.CountryType
import uk.co.jaffakree.allsee.domain.models.types.FeedCounterPartyType
import uk.co.jaffakree.allsee.domain.models.types.FeedDirectionType
import uk.co.jaffakree.allsee.domain.models.types.FeedSourceSubType
import uk.co.jaffakree.allsee.domain.models.types.FeedSourceType
import uk.co.jaffakree.allsee.domain.models.types.FeedSpendingCategory
import java.time.OffsetDateTime
import java.util.UUID

data class FeedItem(
    val feedItemUid: UUID,
    val amount: Balance,
    val transactionTime: OffsetDateTime,
    val settlementTime: OffsetDateTime,
    val direction: FeedDirectionType,
    val source: FeedSourceType,
    val sourceSubType: FeedSourceSubType,
    val counterPartyType: FeedCounterPartyType,
    val counterPartyName: String,
    val counterPartySubEntityName: String?,
    val counterPartySubEntityIdentifier: String,
    val exchangeRate: Int?,
    val totalFees: Int?,
    val totalFeeAmount: Balance?,
    val reference: String,
    val country: CountryType,
    val spendingCategory: FeedSpendingCategory?,
    val userNote: String?,
    // TODO: Implement RoundUp once Goals implemented
    val hasReceipt: Boolean,
    //TODO: Implement BatchPayment
)