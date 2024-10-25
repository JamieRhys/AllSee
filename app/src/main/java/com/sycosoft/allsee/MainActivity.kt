package com.sycosoft.allsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sycosoft.allsee.ui.theme.AllSeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllSeeTheme {
                //AccountAccessPage(viewModel = AccountAccessPageViewModel())
            }
        }
    }
}