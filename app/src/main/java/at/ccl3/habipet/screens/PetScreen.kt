package at.ccl3.habipet.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.viewmodel.HabitViewModel

@Composable
fun PetScreen(navController: NavController, viewModel: HabitViewModel) {
    LazyColumn (
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            Text(text = "Welcome to Pet!")
        }
        // Add more UI elements
    }
}