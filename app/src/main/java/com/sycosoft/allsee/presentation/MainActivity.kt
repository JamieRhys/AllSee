package com.sycosoft.allsee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import com.sycosoft.allsee.presentation.navigation.AppNavigation
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
                    AppNavigation(viewModelFactory = viewModelFactory)
                    //AccountAccessPage(viewModel = viewModel(factory = viewModelFactory))
                    //HomePage(viewModel = viewModel(factory = viewModelFactory))
                }
            }
        }
    }
}