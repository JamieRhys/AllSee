package com.sycosoft.allsee.data.remote.models

import com.squareup.moshi.Json
import com.sycosoft.allsee.domain.models.Identity
import java.time.LocalDate

/** Represents the identity of an account holder */
data class IdentityDto(
    @Json(name = "title") val title: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "dateOfBirth") val dateOfBirth: String,
    @Json(name = "email") val email: String,
    @Json(name = "phone") val phone: String,
)

fun IdentityDto.toDomain(): Identity = Identity(
    title = title,
    firstName = firstName,
    lastName = lastName,
    dob = LocalDate.parse(dateOfBirth),
    email = email,
    phone = phone,
)