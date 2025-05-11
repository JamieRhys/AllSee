package uk.co.jaffakree.allsee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import uk.co.jaffakree.allsee.core.presentation.theme.AllSeeTheme
import uk.co.jaffakree.allsee.presentation.navigation.AppNavigation
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
                }
            }
        }
    }
}