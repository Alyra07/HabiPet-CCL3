package at.ccl3.habipet.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import at.ccl3.habipet.data.Habit

@Composable
fun HabitCheckmarkButton(habit: Habit, onComplete: () -> Unit) {
    IconButton(onClick = onComplete) {
        Icon(Icons.Default.Check, contentDescription = "Complete Habit")
    }
}