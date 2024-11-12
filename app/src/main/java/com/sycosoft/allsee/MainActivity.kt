package com.sycosoft.allsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.presentation.screens.AccountAccessPage
import com.sycosoft.allsee.presentation.ui.theme.AllSeeTheme
import com.sycosoft.allsee.presentation.viewmodels.AccountAccessPageViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var appRepository: AppRepository

    @Inject lateinit var cryptoManager: CryptoManager
    @Inject lateinit var tokenProvider: TokenProvider
    @Inject lateinit var starlingBankApiService: StarlingBankApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AllSeeApplication).appComponent.inject(this)

        enableEdgeToEdge()

        setContent {
            AllSeeTheme {
                Surface {
                    AccountAccessPage(viewModel = AccountAccessPageViewModel(
                        appRepository = appRepository,
                    ))
                }
            }
        }
    }
}