package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.types.AccountHolderType
import java.time.LocalDate
import java.util.UUID

data class Person(
    val uid: UUID,
    val type: AccountHolderType,
    val title: String,
    val firstName: String,
    val lastName: String,
    val dob: LocalDate,
    val email: String,
    val phone: String,
)