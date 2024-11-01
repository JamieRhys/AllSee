package com.sycosoft.allsee.data.remote.model

data class ApiAccount(
    val accountId: String,
    val accountType: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: String,
    val name: String,
)
