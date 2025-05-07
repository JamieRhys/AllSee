package uk.co.jaffakree.allsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import uk.co.jaffakree.allsee.core.presentation.theme.AllSeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllSeeTheme(dynamicColor = false) {
                Surface {
                    Text("Hello strange world!")
                }
            }
        }
    }
}