package uk.co.jaffakree.allsee.domain.models

import java.time.LocalDate

data class Identity(
    val title: String,
    val firstName: String,
    val lastName: String,
    val dob: LocalDate,
    val email: String,
    val phone: String,
)