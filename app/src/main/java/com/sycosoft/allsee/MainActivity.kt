package com.sycosoft.allsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.sycosoft.allsee.data.network.NetworkConnectivityObserver
import com.sycosoft.allsee.presentation.ui.screens.AccountAccessPage
import com.sycosoft.allsee.presentation.ui.screens.HomePage
import com.sycosoft.allsee.presentation.ui.theme.AllSeeTheme
import com.sycosoft.allsee.presentation.ui.viewmodels.AccountAccessPageViewModel
import jakarta.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var accountAccessPageViewModel: AccountAccessPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AllSeeTheme {
                Surface {
                    //AccountAccessPage(viewModel = AccountAccessPageViewModel(application = application))
                    HomePage()
                }
            }
        }
    }
}