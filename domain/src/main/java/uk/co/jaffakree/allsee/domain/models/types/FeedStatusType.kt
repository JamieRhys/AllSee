package uk.co.jaffakree.allsee.domain.models.types

enum class FeedStatusType {
    UPCOMING,
    UPCOMING_CANCELLED,
    PENDING,
    REVERSED,
    SETTLED,
    DECLINED,
    REFUNDED,
    RETRYING,
    ACCOUNT_CHECK
}