package at.ccl3.habipet.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.HabitCompleteCard
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.util.HabitUtils
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun HabitDetailsView(navController: NavController, viewModel: HabitViewModel, habitId: Int) {
    // Get habit by ID from HabitViewModel
    val habit = viewModel.getHabitById(habitId).collectAsState(initial = null).value

    Column(modifier = Modifier.fillMaxSize()) {
        if (habit != null) {
            // HEADER ROW with BACK BUTTON
            TopHeaderBar(
                headingText = "Details",
                navController = navController,
                showBackButton = true
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // COMPLETE HABIT
                HabitCompleteCard(
                    habit = habit,
                    onComplete = { viewModel.completeHabit(habit) },
                    showIcon = true // show habit icon with selected habit.color
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    // EDIT BUTTON
                    IconButton(
                        onClick = { // Navigate to HabitEditView with habit ID
                            navController.navigate("editHabit/${habit.id}")
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Habit",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

                // HABIT DETAILS
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Habit: ${habit.name}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Description: ${habit.description}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Repetition: ${habit.repetition}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Streak: ${habit.streak} of ${HabitUtils.getStreakGoal(habit.repetition)}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}