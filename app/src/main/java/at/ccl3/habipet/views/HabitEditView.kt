package at.ccl3.habipet.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.IconAndColorSelector
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

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW with BACK BUTTON
        TopHeaderBar(headingText = "Edit", navController = navController, showBackButton = true)

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
//            OutlinedTextField(
//                value = streak.toString(),
//                onValueChange = { streak = it.toIntOrNull() ?: 0 },
//                label = { Text("Streak") }
//            )

            // SELECT REPETITION
            RepetitionSelector(currentRepetition = repetition, onRepetitionChange = { repetition = it })

            // ICON & COLOR SELECTOR
            IconAndColorSelector(
                selectedIcon = icon,
                selectedColor = color,
                onIconSelected = { icon = it },
                onColorSelected = { color = it }
            )

            Row( // UPDATE & DELETE HABIT
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // SAVE BUTTON
                Button(onClick = {
                    if (habit != null) {
                        // Update the habit with new data
                        viewModel.update(
                            habit.copy(
                                name = name,
                                description = description,
                                repetition = repetition,
                                streak = streak,
                                icon = icon,
                                color = color
                            )
                        )
                        navController.popBackStack() // Navigate back after saving
                    }
                }) {
                    Text("Save")
                }

                // DELETE BUTTON
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.error, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete Habit",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(2.dp)
                    )
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