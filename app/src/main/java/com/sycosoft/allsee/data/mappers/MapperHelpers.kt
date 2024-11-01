package com.sycosoft.allsee.data.mappers

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

/** Helper functions for mapping between different, universal data types. */
object MapperHelpers {

    fun mapToUUID(uuid: String): UUID = UUID.fromString(uuid)

    fun mapToLocalDateTime(createdAt: String): LocalDateTime = LocalDateTime.ofInstant(Instant.parse(createdAt), ZoneId.systemDefault())
}