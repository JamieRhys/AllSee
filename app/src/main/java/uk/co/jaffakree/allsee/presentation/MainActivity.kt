package uk.co.jaffakree.allsee.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import dagger.android.AndroidInjection
import uk.co.jaffakree.allsee.core.presentation.theme.AllSeeTheme
import uk.co.jaffakree.allsee.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllSeeTheme(dynamicColor = false) {
                Surface {
                    AppNavigation()
                }
            }
        }
    }
}