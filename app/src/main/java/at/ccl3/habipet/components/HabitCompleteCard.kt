package at.ccl3.habipet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.ui.theme.SmokeyGray

@Composable
fun HabitCompleteCard(habit: Habit, onComplete: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    val oneMinuteInMillis = 60 * 1000
    val timeSinceLastCompletion = currentTime - habit.lastCompleted
    val progress = (timeSinceLastCompletion.toFloat() / oneMinuteInMillis).coerceIn(0f, 1f)

    // Calculate the streak progress (x/7 for daily habits)
    val streakProgress = (habit.streak.toFloat() / 7).coerceIn(0f, 1f)
    val streakText = "${habit.streak}/7" // Display the current streak towards the 7-day goal

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = habit.name, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // STREAK PROGRESS BAR
            LinearProgressIndicator(
                progress = { streakProgress },
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                // Display streak progress (x/7 for daily)
                text = "Streak: $streakText",
                style = MaterialTheme.typography.bodySmall
            )

            // Last completed time or "Ready to complete!" message
            Text(
                text = if (progress < 1f) {
                    "Last completed ${timeSinceLastCompletion / oneMinuteInMillis} minutes ago"
                } else {
                    "Ready to complete!"
                },
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            // COMPLETE BUTTON
            IconButton(
                onClick = {
                    if (progress >= 1f) {
                        onComplete()  // Call the onComplete function passed from screen
                    }
                },
                enabled = progress >= 1f
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Complete Habit",
                    tint = if (progress >= 1f) MaterialTheme.colorScheme.primary else SmokeyGray
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}