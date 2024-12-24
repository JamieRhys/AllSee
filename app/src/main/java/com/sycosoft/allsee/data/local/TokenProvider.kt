package com.sycosoft.allsee.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class TokenProvider @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences,
) {
    private val tokenKey = "token"

    /** Retrieves the access token from the encrypted shared preferences. */
    fun getToken(): String? =
        encryptedSharedPreferences.getString(tokenKey, "")

    /** Saves the provided access token to the encrypted shared preferences. */
    fun saveToken(token: String) =
        encryptedSharedPreferences.edit().putString(tokenKey, token).apply()
}