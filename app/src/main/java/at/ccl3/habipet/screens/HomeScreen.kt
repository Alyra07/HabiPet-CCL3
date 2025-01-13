package at.ccl3.habipet.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
    // Collect list of habits from the ViewModel
    val habits = viewModel.allHabits.collectAsState().value

    // Get the 4 most recent habits based on the highest streak
    val recentHabits = habits.sortedByDescending { it.streak }.take(4)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Recent Habits", style = MaterialTheme.typography.headlineSmall
            )
        }
        // display 4 most recent streak habits
        items(recentHabits) { habit ->
            HabitListItem(habit = habit)
        }
    }
}