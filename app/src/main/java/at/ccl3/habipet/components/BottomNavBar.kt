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
import androidx.compose.ui.res.painterResource
import at.ccl3.habipet.R

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", "Home", BottomNavIcon.DrawableResource(R.drawable.home_calendar), BottomNavIcon.DrawableResource(R.drawable.home_calendar)),
        BottomNavItem("pet", "Pet", BottomNavIcon.ImageVectorIcon(Icons.Outlined.FavoriteBorder), BottomNavIcon.ImageVectorIcon(Icons.Filled.Favorite)),
        BottomNavItem("add_habit", "Add Habit", BottomNavIcon.ImageVectorIcon(Icons.Outlined.Add), BottomNavIcon.ImageVectorIcon(Icons.Filled.Add), true),
        BottomNavItem("habits", "Habits", BottomNavIcon.DrawableResource(R.drawable.habits_outlined), BottomNavIcon.DrawableResource(R.drawable.habits_filled)),
        BottomNavItem("shop", "Shop", BottomNavIcon.ImageVectorIcon(Icons.Outlined.ShoppingCart), BottomNavIcon.ImageVectorIcon(Icons.Filled.ShoppingCart))
    )

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(4.dp)
    ) {
        NavigationBar {
            val currentDestination by navController.currentBackStackEntryAsState()
            items.forEach { item ->
                val isSelected = currentDestination?.destination?.route == item.route

                NavigationBarItem(
                    icon = {
                        when (val icon = if (isSelected) item.filledIcon else item.outlinedIcon) {
                            // Icon from /res/drawable
                            is BottomNavIcon.DrawableResource -> {
                                Icon(
                                    painter = painterResource(id = icon.id),
                                    contentDescription = item.label
                                )
                            }
                            // Handle Material Icons differently
                            is BottomNavIcon.ImageVectorIcon -> {
                                Icon(
                                    imageVector = icon.imageVector,
                                    contentDescription = item.label
                                )
                            }
                        }
                    },
                    label = { if (!item.isSpecial) { Text(text = item.label) } },
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) }
                )
            }
        }
    }
}

sealed class BottomNavIcon {
    data class DrawableResource(val id: Int) : BottomNavIcon()
    data class ImageVectorIcon(val imageVector: androidx.compose.ui.graphics.vector.ImageVector) : BottomNavIcon()
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val outlinedIcon: BottomNavIcon,
    val filledIcon: BottomNavIcon,
    val isSpecial: Boolean = false
)