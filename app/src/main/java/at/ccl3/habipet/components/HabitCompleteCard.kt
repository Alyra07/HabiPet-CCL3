package at.ccl3.habipet.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.R
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.ui.theme.SmokeyGray
import at.ccl3.habipet.util.HabitUtils

@Composable
fun HabitCompleteCard(habit: Habit, onComplete: () -> Unit) {
    val currentTime = System.currentTimeMillis()
    val timeSinceLastCompletion = currentTime - habit.lastCompleted
    val oneMinuteInMillis = 60_000

    // Calculate time progress & get streak goal based on repetition type
    val durationMillis = HabitUtils.getDurationMillis(habit.repetition)
    val (timeProgress, streakGoal) = HabitUtils.calculateTimeProgress(
        timeSinceLastCompletion,
        habit.repetition
    )
    // Calculate streak progress for progress bar
    val streakProgress = ((habit.streak % streakGoal).toFloat() / streakGoal).coerceIn(0f, 1f)
    val streakText = "${habit.streak % streakGoal}/$streakGoal"

    // Calculate coins you get for completing streak goal (full progress bar)
    val coinsToAward = HabitUtils.getCoinsToAward(habit.repetition)

    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Column( // general Card content
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // HABIT NAME
                Text(text = habit.name, style = MaterialTheme.typography.titleLarge)

                // Coins you get for completing streak goal (full progress bar)
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$coinsToAward", style = MaterialTheme.typography.bodyMedium)
                    Image(
                        painter = painterResource(id = R.drawable.coin_icon),
                        contentDescription = "Coins",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // STREAK PROGRESS BAR
            LinearProgressIndicator(
                progress = { streakProgress },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(),
                gapSize = 0.dp
            )

            Row( // other content
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row( // TIME PROGRESS & BUTTON
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // COMPLETE BUTTON
                    IconButton(
                        modifier = Modifier.size(34.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        onClick = {
                            if (timeProgress >= 1f) {
                                onComplete() // Call onComplete function provided by the screen
                            }
                        },
                        enabled = timeProgress >= 1f
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            modifier = Modifier
                                .fillMaxSize()
                                .border(1.dp, SmokeyGray, CircleShape),
                            contentDescription = "Complete Habit",
                            tint = if (timeProgress >= 1f) MaterialTheme.colorScheme.primary else SmokeyGray
                        )
                    }

                    // TIME PROGRESS TEXT
                    Text(
                        text = if (timeProgress < 1f) {
                            val remainingTimeMillis = (1f - timeProgress) * durationMillis
                            val remainingMinutes = (remainingTimeMillis / oneMinuteInMillis).toInt()
                            "Ready in ${remainingMinutes / 60} hours ${remainingMinutes % 60} minutes"
                        } else {
                            "Ready to complete!"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                // STREAK GOAL PROGRESS TEXT (below progress bar)
                Text(text = "Goal: $streakText", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}