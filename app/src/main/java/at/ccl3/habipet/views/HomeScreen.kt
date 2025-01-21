package at.ccl3.habipet.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.*
import at.ccl3.habipet.util.HabitUtils
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
            val durationMillis = HabitUtils.getDurationMillis(habit.repetition)

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

            // HABIT COMPLETION CALENDAR
            item {
                Spacer(modifier = Modifier.height(8.dp))
                HabitCalendar(habits = habits)
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
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}