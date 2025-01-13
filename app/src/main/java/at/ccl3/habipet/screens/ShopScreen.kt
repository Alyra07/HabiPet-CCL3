package at.ccl3.habipet.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import at.ccl3.habipet.viewmodel.HabitViewModel

@Composable
fun ShopScreen(navController: NavController, viewModel: HabitViewModel) {
    Text(text = "Welcome to the Shop Screen")
    // Add more UI elements
}