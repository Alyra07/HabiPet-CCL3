package at.ccl3.habipet.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Outlined.Home, Icons.Filled.Home),
        BottomNavItem("pet", "Pet", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite),
        BottomNavItem("add_habit", "Add", Icons.Outlined.Add, Icons.Filled.Add, isSpecial = true),
        BottomNavItem("habits", "Habits", Icons.Outlined.CheckCircle, Icons.Filled.CheckCircle),
        BottomNavItem("shop", "Shop", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart)
    )

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBar {
            val currentDestination by navController.currentBackStackEntryAsState()
            items.forEach { item ->
                val isSelected = currentDestination?.destination?.route == item.route

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                            contentDescription = item.label
                        )
                    },
                    modifier = if (item.isSpecial) Modifier.padding(bottom = 12.dp) else Modifier,
                    label = {
                        if (!item.isSpecial) {
                            Text(text = item.label)
                        }
                    },
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) }
                )
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    val isSpecial: Boolean = false
)