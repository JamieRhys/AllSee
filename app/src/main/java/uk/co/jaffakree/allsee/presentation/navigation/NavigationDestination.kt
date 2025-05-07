package uk.co.jaffakree.allsee.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument

/** Provides the navigation destinations for the app.
 *
 * @property title The title of the destination. This is displayed on the bottom navigation bar.
 * @property route The route of the destination.
 * @property showOnNavBar Whether the destination should be shown on the bottom navigation bar.
 * @property showNavBar Whether to show the bottom navigation bar on the page.
 * @property selectedIcon The icon to show when the destination is selected (and displayed).
 * @property unselectedIcon The icon to show when the destination is not selected.
 * @property args The arguments to be passed to the destination.
 */
internal enum class NavigationDestination(
    val title: String? = null,
    val route: String,
    val showOnNavBar: Boolean = false,
    val showNavBar: Boolean = false,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val args: List<NamedNavArgument> = emptyList(),
) {
    Home(
        title = "Home",
        route = "home",
        showOnNavBar = true,
        showNavBar = true,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    Login(
        route = "login",
    ),
    AccountDetails(
        route = "account_details",
    )
}