package uk.co.jaffakree.allsee.remote.models

import java.util.UUID

data class FeedItemsDto(
    val feedItems: List<FeedItemDto>,
)

data class FeedItemDto(
    val feedItemUid: UUID,
    val categoryUid: UUID?,
    val amount: BalanceDto,
    val sourceAmount: BalanceDto?,
    val direction: String,
    val updatedAt: String?,
    val transactionTime: String?,
    val settlementTime: String,
    val retryAllocationUntilTime: String?,
    val source: String,
    val sourceSubType: String?,
    val status: String?,
    val transactingApplicationUserUid: UUID?,
    val counterPartyType: String,
    val counterPartyUid: UUID?,
    val counterPartyName: String,
    val counterPartySubEntityUid: UUID?,
    val counterPartySubEntityName: String?,
    val counterPartySubEntityIdentifier: String?,
    val counterPartySubEntitySubIdentifier: String?,
    val exchangeRate: Int?, // ?
    val totalFees: Int?, // ?
    val totalFeeAmount: BalanceDto?,
    val reference: String,
    val country: String, // Array
    val spendingCategory: String?,
    val userNote: String?,
    val roundUp: AssociatedFeedRoundUpDto?,
    val hasAttachment: Boolean?,
    val hasReceipt: Boolean,
    val batchPaymentDetails: BatchPaymentDetails?,
)

data class AssociatedFeedRoundUpDto(
    val goalCategoryUid: String,
    val amount: BalanceDto,
)

data class BatchPaymentDetails(
    val batchPaymentUid: String,
    val batchPaymentType: String, // Enum
)