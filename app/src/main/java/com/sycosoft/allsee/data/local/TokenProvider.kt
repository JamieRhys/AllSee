package com.sycosoft.allsee.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class TokenProvider @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences,
) {
    private val tokenKey = "token"

    fun getToken(): String? =
        encryptedSharedPreferences.getString(tokenKey, "")

    fun saveToken(token: String) =
        encryptedSharedPreferences.edit().putString(tokenKey, token).apply()
}