package com.sycosoft.allsee.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.network.NetworkConnectivityObserver
import com.sycosoft.allsee.domain.network.ConnectivityObserver
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class AccountAccessPageViewModel(
    application: Application,

) : AndroidViewModel(application) {
    private val _decryptedToken: MutableStateFlow<String> = MutableStateFlow("No Data")
    val decryptedToken: StateFlow<String> = _decryptedToken

    private val _networkStatus: MutableStateFlow<ConnectivityObserver.Status> = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _networkStatus

    private val cryptoManager = CryptoManager()

    init {
        val connectivityObserver = NetworkConnectivityObserver(application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

        viewModelScope.launch {
            connectivityObserver.observe().collect {
                _networkStatus.value = it
            }
        }
    }

    fun encryptToken(token: String): Boolean {
        val bytes = token.toByteArray()
        val file = File(getApplication<Application>().applicationContext.filesDir, "token.txt")

        if (!file.exists()) {
            file.createNewFile()
        }

        val fos = file.outputStream()
        val encryptedToken = cryptoManager.encrypt(
            bytes = bytes,
            outputStream = fos
        ).decodeToString()

        decryptToken()

        return encryptedToken.isBlank()
    }

    fun decryptToken() {
        val file = File(getApplication<Application>().applicationContext.filesDir, "token.txt")
        _decryptedToken.value = cryptoManager.decrypt(
            inputStream = file.inputStream()
        ).decodeToString()
    }
}