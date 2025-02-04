package com.sycosoft.allsee.presentation.navigation

import androidx.navigation.NamedNavArgument

sealed class NavigationDestination(val route: String, val args: List<NamedNavArgument> = emptyList()) {
    data object AccountAccess : NavigationDestination("account_access")
    data object AccountDetails : NavigationDestination("account_details")
    data object Home : NavigationDestination("home")
}