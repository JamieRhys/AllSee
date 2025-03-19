package uk.co.jaffakree.allsee.remote.models

data class FeedItemsDto(
    val feedItems: List<FeedItemDto>,
)

data class FeedItemDto(
    val feedItemUid: String?,
    val categoryUid: String?,
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
    val transactingApplicationUserUid: String?,
    val counterPartyType: String,
    val counterPartyUid: String?,
    val counterPartyName: String,
    val counterPartySubEntityUid: String?,
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