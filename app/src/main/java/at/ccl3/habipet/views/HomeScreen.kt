package at.ccl3.habipet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import at.ccl3.habipet.components.HabitCompleteCard
import at.ccl3.habipet.components.HabitListItem
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.viewmodels.HabitViewModel
import at.ccl3.habipet.viewmodels.PetViewModel

@Composable
fun HomeScreen(navController: NavController, habitViewModel: HabitViewModel, petViewModel: PetViewModel) {
    // collect list of habits from the HabitViewModel
    val habits = habitViewModel.allHabits.collectAsState().value

    // Get the 3 most recent habits based on the highest streak
    val recentHabits = habits.sortedByDescending { it.streak }.take(3)

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW with COINS
        TopHeaderBar(headingText = "HabiPet Home", navController = navController, petViewModel = petViewModel)

        // HOME SCREEN CONTENT
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            item { // COMPLETE HABITS SECTION
                Text(text = "Complete Habit", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Display habits to be completed with completion countdown
            items(habits) { habit ->
                HabitCompleteCard(habit) { habitViewModel.completeHabit(habit) }
            }

            item { // RECENT HABITS SECTION
                Text(
                    text = "Recent Habits", style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
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