package uk.co.jaffakree.allsee.presentation.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.co.jaffakree.allsee.presentation.navigation.NavigationDestination

@Composable
internal fun BottomNavigationBar(
    navController: NavController,
    selectedItem: NavigationDestination,
) {
    if (selectedItem.showNavBar) {
        NavigationBar(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                    )
                ),
            containerColor = Color.Transparent,
        ) {
            NavigationDestination.entries.forEachIndexed { index, item ->
                if (item.showOnNavBar) {
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent),
                        selected = selectedItem.ordinal == index,
                        onClick = { navController.navigate(item.route) },
                        label = { Text(item.title ?: "" ) },
                        icon = {
                            BadgedBox(
                                badge = {},
                            ) {
                                Icon(
                                    imageVector = if (selectedItem.ordinal == index) item.selectedIcon!! else item.unselectedIcon!!,
                                    contentDescription = item.title,
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}