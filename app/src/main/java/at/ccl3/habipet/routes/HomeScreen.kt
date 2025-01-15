package at.ccl3.habipet.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.HabitCheckmarkButton
import at.ccl3.habipet.components.HabitListItem
import at.ccl3.habipet.components.HeaderWithLogo
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HabitViewModel) {
    // collect list of habits from the HabitViewModel
    val habits = viewModel.allHabits.collectAsState().value
    // get current time for habit completion
    val currentTime = System.currentTimeMillis()
    val oneDayInMillis = 24 * 60 * 60 * 1000
    // Get the 3 most recent habits based on the highest streak
    val recentHabits = habits.sortedByDescending { it.streak }.take(3)

    Column (modifier = Modifier.fillMaxSize()) {
        // HEADER ROW
        HeaderWithLogo(headingText = "Welcome to HabiPet!", navController = navController)

        // HOME SCREEN UI
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text(text = "Complete Habit", style = MaterialTheme.typography.headlineSmall)
            }

            items(habits) { habit ->
                val timeSinceLastCompletion = currentTime - habit.lastCompleted
                val progress = (timeSinceLastCompletion.toFloat() / oneDayInMillis).coerceIn(0f, 1f)

                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = habit.name, style = MaterialTheme.typography.bodyLarge)
                    // Linear progress bar for habit completion time
                    LinearProgressIndicator(progress = { progress } )
                    if (progress >= 1f) {
                        HabitCheckmarkButton(habit) {
                            viewModel.completeHabit(habit)
                        }
                    }
                }
            }
            item { // RECENT HABITS SECTION
                Text(
                    text = "Recent Habits", style = MaterialTheme.typography.headlineSmall
                )
            }
            // display 3 most recent streak habits
            items(recentHabits) { habit ->
                HabitListItem(habit = habit) {
                    val habitId = habit.id
                    // pass habit ID to HabitDetailsView
                    navController.navigate("habitDetails/$habitId")
                }
            }
        }
    }
}