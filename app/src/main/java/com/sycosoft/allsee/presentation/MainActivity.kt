package com.sycosoft.allsee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sycosoft.allsee.presentation.screens.AccountAccessPage
import com.sycosoft.allsee.presentation.theme.AllSeeTheme
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AllSeeTheme(dynamicColor = false) {
                Surface {
                    AccountAccessPage(viewModel = viewModel(factory = viewModelFactory))
                }
            }
        }
    }
}