package uk.co.jaffakree.allsee.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.co.jaffakree.allsee.core.presentation.theme.mainBackground
import uk.co.jaffakree.allsee.presentation.components.navigation.BottomNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val selectedItem by rememberSaveable { mutableStateOf(NavigationDestination.Login) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                selectedItem = selectedItem,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .mainBackground()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationDestination.Login.route
            ) {
                composable(NavigationDestination.Login.route) {
                    Text("Hello from Login Screen.")
                }
            }
        }
    }
}