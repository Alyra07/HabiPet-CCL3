package at.ccl3.habipet.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.viewmodel.HabitViewModel

@Composable
fun HabitEditView(navController: NavController, viewModel: HabitViewModel, habitId: Int) {

    val habit = viewModel.getHabitById(habitId).collectAsState(initial = null).value
    // Initialize habit state variables
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

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
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
            modifier = Modifier.fillMaxWidth()
        )
        // REPETITION & STREAK
        OutlinedTextField(
            value = repetition,
            onValueChange = { repetition = it },
            label = { Text("Repetition") }
        )
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
    }
}