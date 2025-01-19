package at.ccl3.habipet.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.components.RepetitionSelector
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun HabitEditView(navController: NavController, viewModel: HabitViewModel, habitId: Int) {
    var showDialog by remember { mutableStateOf(false) } // delete habit confirmation dialog

    // Initialize habit state variables
    val habit = viewModel.getHabitById(habitId).collectAsState(initial = null).value

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var repetition by remember { mutableStateOf("") }
    var streak by remember { mutableIntStateOf(0) }
    var icon by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }

    // Load habit data into state variables
    LaunchedEffect(habit) {
        if (habit != null) {
            name = habit.name
            description = habit.description
            repetition = habit.repetition
            streak = habit.streak
            icon = habit.icon
            color = habit.color
        }
    }

    Column (modifier = Modifier.fillMaxSize()){
        // HEADER ROW with BACK BUTTON
        TopHeaderBar(headingText = "Edit Habit", navController = navController, showBackButton = true)

        // EDIT HABIT
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // NAME & DESCRIPTION
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // REPETITION & STREAK
            RepetitionSelector(currentRepetition = repetition, onRepetitionChange = { repetition = it })

            OutlinedTextField(
                value = streak.toString(),
                onValueChange = { streak = it.toIntOrNull() ?: 0 },
                label = { Text("Streak") }
            )

            // ICON & COLOR
            OutlinedTextField(
                value = icon,
                onValueChange = { icon = it },
                label = { Text("Icon") }
            )
            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") }
            )

            Row ( // UPDATE & DELETE HABIT
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // SAVE BUTTON
                Button(onClick = {
                    if (habit != null) {
                        // Update the habit with new data
                        viewModel.update(habit.copy(
                            name = name,
                            description = description,
                            repetition = repetition,
                            streak = streak,
                            icon = icon,
                            color = color
                        ))
                        navController.popBackStack() // Navigate back after saving
                    }
                }) {
                    Text("Save")
                }

                // DELETE BUTTON
                IconButton(
                    onClick = { showDialog = true },
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Habit")
                }
            }

            // DELETE CONFIRMATION DIALOG
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Delete Habit") },
                    text = { Text("Are you sure you want to delete this habit? :(") },
                    confirmButton = {
                        TextButton(onClick = {
                            if (habit != null) {
                                // Delete habit via HabitViewModel
                                viewModel.delete(habit)
                                navController.navigate("home") // Navigate to Home after deleting
                            }
                        }) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}