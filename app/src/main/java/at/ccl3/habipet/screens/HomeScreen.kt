package at.ccl3.habipet.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.HabitListItem
import at.ccl3.habipet.viewmodel.HabitViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HabitViewModel) {
    // Collect the list of habits from the ViewModel
    val habits = viewModel.allHabits.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(text = "Welcome to HabiPet!")
        }
        // Display habits from the ViewModel
        items(habits) { habit ->
            HabitListItem(habit = habit)
        }
    }
}