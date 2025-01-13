package at.ccl3.habipet.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.viewmodel.HabitViewModel

@Composable
fun HabitDetailsView(navController: NavController, viewModel: HabitViewModel, habitId: Int) {
    val habit = viewModel.getHabitById(habitId).collectAsState(initial = null).value
    if (habit != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Text(text = habit.name, style = MaterialTheme.typography.titleLarge) }

            item {
                Text(text = habit.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Repetition: ${habit.repetition}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Streak: ${habit.streak}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Icon: ${habit.icon}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Color: ${habit.color}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}