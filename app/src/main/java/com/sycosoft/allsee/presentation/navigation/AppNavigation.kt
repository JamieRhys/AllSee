package com.sycosoft.allsee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sycosoft.allsee.presentation.pages.AccountAccessPage
import uk.co.jaffakree.allsee.feature_accountdetails.page.AccountDetailsPage
import com.sycosoft.allsee.presentation.pages.HomePage

@Composable
fun AppNavigation(
    viewModelFactory: ViewModelProvider.Factory
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.AccountAccess.route
    ) {
        composable(NavigationDestination.AccountAccess.route) {
            AccountAccessPage(
                viewModel = viewModel(factory = viewModelFactory),
                onNavigateToHomePage = {
                    navController.navigate(NavigationDestination.Home.route)
                },
            )
        }
        composable(NavigationDestination.AccountDetails.route) {
            AccountDetailsPage(
                viewModel = viewModel(factory = viewModelFactory),
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(NavigationDestination.Home.route) {
            HomePage(
                viewModel = viewModel(factory = viewModelFactory),
                onBalanceCardClick = {
                    navController.navigate(NavigationDestination.AccountDetails.route)
                }
            )
        }

    }
}