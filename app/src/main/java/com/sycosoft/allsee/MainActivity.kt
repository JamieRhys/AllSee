package com.sycosoft.allsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sycosoft.allsee.data.local.CryptoManager
import com.sycosoft.allsee.data.local.TokenProvider
import com.sycosoft.allsee.data.remote.services.StarlingBankApiService
import com.sycosoft.allsee.domain.repository.AppRepository
import com.sycosoft.allsee.presentation.screens.AccountAccessPage
import com.sycosoft.allsee.presentation.ui.theme.AllSeeTheme
import com.sycosoft.allsee.presentation.viewmodels.AccountAccessPageViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AllSeeTheme {
                Surface {
                    AccountAccessPage(viewModel = viewModel(factory = viewModelFactory))
                }
            }
        }
    }
}