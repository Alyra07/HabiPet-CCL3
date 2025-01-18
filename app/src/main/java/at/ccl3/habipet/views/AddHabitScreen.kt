package at.ccl3.habipet.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.components.RepetitionSelector
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun AddHabitScreen(navController: NavController, viewModel: HabitViewModel) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var repetition by rememberSaveable { mutableStateOf("Daily") } // default repetition
    var icon by rememberSaveable { mutableStateOf("") }
    var color by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW without coins
        TopHeaderBar(headingText = "Add New Habit", navController = navController)

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TEXT FIELDS
            item {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Habit Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // CHOOSE HABIT REPETITION
            item {
                RepetitionSelector(currentRepetition = repetition, onRepetitionChange = { repetition = it })
            }

            // CHOOSE ICON AND COLOR
            item {
                TextField(
                    value = icon,
                    onValueChange = { icon = it },
                    label = { Text("Icon") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                TextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { // SAVE BUTTON
                Button(
                    onClick = {
                        if (name.isNotEmpty() && description.isNotEmpty() && repetition.isNotEmpty()) {
                            val newHabit = Habit(
                                name = name,
                                description = description,
                                repetition = repetition,
                                icon = icon,
                                color = color
                            )
                            // Insert new habit via HabitViewModel
                            viewModel.insert(newHabit)
                            navController.navigateUp() // Navigate back after saving
                        } else {
                            // Handle empty fields
                            Log.d("AddHabitScreen", "Name, description, or repetition is empty")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Save New Habit")
                }
            }
        }
    }
}