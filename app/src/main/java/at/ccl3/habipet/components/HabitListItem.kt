package at.ccl3.habipet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.data.Habit

@Composable
fun HabitListItem(habit: Habit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = habit.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = habit.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Repetition: ${habit.repetition}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
