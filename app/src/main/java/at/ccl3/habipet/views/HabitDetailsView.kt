package at.ccl3.habipet.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun HabitDetailsView(navController: NavController, viewModel: HabitViewModel, habitId: Int) {
    // Get habit by ID from HabitViewModel
    val habit = viewModel.getHabitById(habitId).collectAsState(initial = null).value

    Column(modifier = Modifier.fillMaxSize()) {
        if (habit != null) {
            // HEADER ROW with BACK BUTTON
            TopHeaderBar(headingText = "Details", navController = navController, showBackButton = true)

            // DETAILS SECTION (needs work)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // COMPLETE HABIT
                item {
                    HabitCompleteCard(habit = habit) { viewModel.completeHabit(habit) }
                }
                item { // Habit Description, other details
                    Text(text = habit.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Repetition: ${habit.repetition}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Streak: ${habit.streak}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = "Icon: ${habit.icon}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "Color: ${habit.color}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                // EDIT BUTTON
                item {
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
            }
        }
    }
}