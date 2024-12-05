package com.sycosoft.allsee.domain.models

import com.sycosoft.allsee.domain.models.types.AccountHolderType
import java.time.LocalDate

data class Person(
    val id: Int = 0,
    val uid: String,
    val type: AccountHolderType,
    val title: String,
    val firstName: String,
    val lastName: String,
    val dob: LocalDate,
    val email: String,
    val phone: String,
)