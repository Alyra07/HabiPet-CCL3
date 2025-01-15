package at.ccl3.habipet.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.ui.theme.SmokeyGray

@Composable
fun HabitCompleteCard(habit: Habit, onComplete: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    val timeSinceLastCompletion = currentTime - habit.lastCompleted
    val oneMinuteInMillis = 60_000

    // Calculate time progress based on repetition type
    val (timeProgress, streakGoal, durationMillis) = when (habit.repetition) {
        "Daily" -> Triple(timeSinceLastCompletion.toFloat() / 86_400_000, 7, 86_400_000)
        "Weekly" -> Triple(timeSinceLastCompletion.toFloat() / 604_800_000, 4, 604_800_000)
        "Monthly" -> Triple(timeSinceLastCompletion.toFloat() / 2_592_000_000, 2, 2_592_000_000)
        "Test" -> Triple(timeSinceLastCompletion.toFloat() / oneMinuteInMillis, 2, oneMinuteInMillis)
        else -> Triple(0f, 1, 1)
    }.let { Triple(it.first.coerceIn(0f, 1f), it.second, it.third) }

    // Calculate streak progress for progress bar
    val streakProgress = ((habit.streak % streakGoal).toFloat() / streakGoal).coerceIn(0f, 1f)
    val streakText = "${habit.streak % streakGoal}/$streakGoal"

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

            // STREAK PROGRESS BAR
            LinearProgressIndicator(
                progress = { streakProgress },
                modifier = Modifier.fillMaxWidth(),
            )
            Text(text = "Streak: $streakText", style = MaterialTheme.typography.bodySmall)

            // TIME PROGRESS TEXT
            Text(
                text = if (timeProgress < 1f) {
                    val remainingTimeMillis = (1f - timeProgress) * durationMillis.toFloat()
                    val remainingMinutes = (remainingTimeMillis / oneMinuteInMillis).toInt()
                    "Ready in ${remainingMinutes / 60} hours ${remainingMinutes % 60} minutes"
                } else {
                    "Ready to complete!"
                },
                style = MaterialTheme.typography.bodySmall
            )

            // COMPLETE BUTTON
            IconButton(
                onClick = {
                    if (timeProgress >= 1f) {
                        onComplete() // Call onComplete function provided by the screen
                    }
                },
                enabled = timeProgress >= 1f
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Complete Habit",
                    tint = if (timeProgress >= 1f) MaterialTheme.colorScheme.primary else SmokeyGray
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}