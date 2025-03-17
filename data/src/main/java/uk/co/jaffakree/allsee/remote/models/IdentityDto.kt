package uk.co.jaffakree.allsee.remote.models

import com.squareup.moshi.Json

/** Represents the identity of an account holder */
data class IdentityDto(
    @Json(name = "title") val title: String,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "dateOfBirth") val dateOfBirth: String,
    @Json(name = "email") val email: String,
    @Json(name = "phone") val phone: String,
)