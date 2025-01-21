package at.ccl3.habipet.views

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.IconAndColorSelector
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.components.RepetitionSelector
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun AddHabitScreen(navController: NavController, viewModel: HabitViewModel) {
    // Get values from input fields
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var repetition by rememberSaveable { mutableStateOf("Daily") }
    var icon by rememberSaveable { mutableStateOf("paw_default") }
    var color by rememberSaveable { mutableStateOf("Default") }

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW without coins
        TopHeaderBar(headingText = "Add New Habit", navController = navController)

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                Spacer(modifier = Modifier.height(16.dp))
            }

            // CHOOSE ICON AND COLOR
            item {
                IconAndColorSelector(
                    selectedIcon = icon,
                    selectedColor = color,
                    onIconSelected = { icon = it },
                    onColorSelected = { color = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item { // SAVE BUTTON
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (name.isNotEmpty() && description.isNotEmpty() && repetition.isNotEmpty()) {
                            val newHabit = Habit(
                                name = name,
                                description = description,
                                repetition = repetition,
                                icon = icon,
                                color = color
                            )
                            viewModel.insert(newHabit)
                            navController.navigateUp()
                        } else {
                            Log.d("AddHabitScreen", "Name, description, or repetition is empty")
                        }
                    },
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