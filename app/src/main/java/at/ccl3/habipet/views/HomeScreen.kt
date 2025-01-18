package at.ccl3.habipet.views

import androidx.compose.foundation.layout.Arrangement
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
fun HomeScreen(
    navController: NavController,
    habitViewModel: HabitViewModel,
    petViewModel: PetViewModel
) {
    val habits = habitViewModel.allHabits.collectAsState().value

    // Find the next habit ready to complete or the one closest to readiness
    val nextHabitToComplete = habits
        .minByOrNull { habit ->
            val currentTime = System.currentTimeMillis()
            val timeSinceLastCompletion = currentTime - habit.lastCompleted
            val oneMinuteInMillis = 60_000L // Long representation of 60 seconds
            val durationMillis = when (habit.repetition) {
                "Daily" -> 86_400_000L
                "Weekly" -> 604_800_000L
                "Monthly" -> 2_592_000_000L
                "Test" -> oneMinuteInMillis
                else -> Long.MAX_VALUE
            }
            durationMillis - timeSinceLastCompletion
        }

    // Get 4 most recent habits with highest streak
    val recentHabits = habits.sortedByDescending { it.streak }.take(4)

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW with LOGO & COINS
        TopHeaderBar(
            headingText = "HabiPet Home",
            navController = navController,
            petViewModel = petViewModel
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // NEXT HABIT TO COMPLETE
            item {
                Text(text = "Complete Habit", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                nextHabitToComplete?.let { habit ->
                    // call habitViewModel.completeHabit() when the habit is completed
                    HabitCompleteCard(habit) { habitViewModel.completeHabit(habit) }
                }
            }

            // RECENT HABITS
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Recent Habits", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }
            // Display 4 most recent habits with highest streak
            items(recentHabits) { habit ->
                HabitListItem(habit = habit) {
                    val habitId = habit.id
                    navController.navigate("habitDetails/$habitId")
                }
            }
        }
    }
}