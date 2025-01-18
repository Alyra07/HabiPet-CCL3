package at.ccl3.habipet.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.res.painterResource
import at.ccl3.habipet.R

// Single item in BottomNavBar
data class BottomNavItem(
    val route: String,
    val label: String,
    val outlinedIcon: Int,
    val filledIcon: Int,
    val isSpecial: Boolean = false // add habit button
)

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        // ROUTES
        BottomNavItem(
            route = "home",
            label = "Home",
            outlinedIcon = R.drawable.nav_home_outlined,
            filledIcon = R.drawable.nav_home_filled,
        ),
        BottomNavItem(
            route = "pet",
            label = "Pet",
            outlinedIcon = R.drawable.nav_pet_outlined,
            filledIcon = R.drawable.nav_pet_filled,
        ),
        BottomNavItem(
            route = "add_habit",
            label = "Add Habit",
            outlinedIcon = R.drawable.nav_add_outlined,
            filledIcon = R.drawable.nav_add_filled,
            isSpecial = true // has unique icon size
        ),
        BottomNavItem(
            route = "habits",
            label = "Habits",
            outlinedIcon = R.drawable.nav_habits_outlined,
            filledIcon = R.drawable.nav_habits_filled,
        ),
        BottomNavItem(
            route = "shop",
            label = "Shop",
            outlinedIcon = R.drawable.nav_shop_outlined,
            filledIcon = R.drawable.nav_shop_filled,
        )
    )

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer // NavBar color
    ) {
        // NAVIGATION ITEMS
        NavigationBar {
            val currentDestination by navController.currentBackStackEntryAsState()
            items.forEach { item ->
                val isSelected = currentDestination?.destination?.route == item.route

                NavigationBarItem(
                    icon = {
                        // check if icon is filled or outlined (isSelected)
                        when (val icon = if (isSelected) item.filledIcon else item.outlinedIcon) {
                            // Icons from /res/drawable
                            (icon) -> {
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = item.label,
                                    modifier = if (item.isSpecial) Modifier.size(34.dp) else Modifier.size(
                                        24.dp
                                    ),
                                    tint = if (isSelected) MaterialTheme.colorScheme.onSecondary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    // show icon label for all items except "add habit"
                    label = {
                        if (!item.isSpecial) {
                            Text(text = item.label)
                        }
                    },
                    selected = isSelected,
                    onClick = { navController.navigate(item.route) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}