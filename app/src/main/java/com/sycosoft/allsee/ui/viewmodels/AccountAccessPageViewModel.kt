package com.sycosoft.allsee.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.sycosoft.allsee.data.local.CryptoManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class AccountAccessPageViewModel(private val filesDir: File) : ViewModel() {
    private val _decryptedToken: MutableStateFlow<String> = MutableStateFlow("No Data")
    val decryptedToken: StateFlow<String> = _decryptedToken

    private val cryptoManager = CryptoManager()

    fun encryptToken(token: String) {
        val bytes = token.toByteArray()
        val file = File(filesDir, "token.txt")

        if (!file.exists()) {
            file.createNewFile()
        }

        val fos = file.outputStream()
        _decryptedToken.value = cryptoManager.encrypt(
            bytes = bytes,
            outputStream = fos
        ).decodeToString()
    }

    fun decryptToken() {
        val file = File(filesDir, "token.txt")
        _decryptedToken.value = cryptoManager.decrypt(
            inputStream = file.inputStream()
        ).decodeToString()
    }
}