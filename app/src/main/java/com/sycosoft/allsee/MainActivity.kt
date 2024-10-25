package com.sycosoft.allsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sycosoft.allsee.ui.screens.AccountAccessPage
import com.sycosoft.allsee.ui.theme.AllSeeTheme
import com.sycosoft.allsee.ui.viewmodels.AccountAccessPageViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllSeeTheme {
                AccountAccessPage(viewModel = AccountAccessPageViewModel(filesDir))
            }
        }
    }
}