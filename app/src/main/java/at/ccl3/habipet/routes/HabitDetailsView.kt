package at.ccl3.habipet.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.HeaderWithLogo
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun HabitDetailsView(navController: NavController, viewModel: HabitViewModel, habitId: Int) {
    // Get habit by ID from HabitViewModel
    val habit = viewModel.getHabitById(habitId).collectAsState(initial = null).value

    Column(modifier = Modifier.fillMaxSize()) {
        if (habit != null) {
            // HEADER ROW with back button
            HeaderWithLogo(headingText = habit.name, navController = navController, showBackButton = true)

            // DETAILS SECTION (needs work)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
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

                        ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Habit")
                    }
                }
            }
        }
    }
}