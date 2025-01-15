package at.ccl3.habipet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Component to select repetition options in HabitEditView & AddHabitScreen
@Composable
fun RepetitionSelector(currentRepetition: String, onRepetitionChange: (String) -> Unit) {
    Text("Repetition")
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        listOf("Daily", "Weekly", "Monthly", "Test").forEach { option ->
            Button(
                onClick = { onRepetitionChange(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentRepetition == option) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                    contentColor = if (currentRepetition == option) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(option)
            }
        }
    }
}