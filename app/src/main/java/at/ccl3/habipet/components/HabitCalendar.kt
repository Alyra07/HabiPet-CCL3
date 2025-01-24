package at.ccl3.habipet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.data.Habit
import java.util.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HabitCalendar(habits: List<Habit>) {
    // Create a Calendar instance to get the current date
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
    val currentYear = calendar.get(Calendar.YEAR)

    // Find the number of days in the current month
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    // Generate a list of dates for the month
    val daysList = (1..daysInMonth).toList()

    // Check which days have habit completions
    val completedDays = habits.map { habit ->
        val completionDate = Calendar.getInstance().apply {
            timeInMillis = habit.lastCompleted
        }
        completionDate.get(Calendar.DAY_OF_MONTH)
    }.toSet()

    // Display the days in a grid with 7 columns (week)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // HEADING
            Text(text = "$currentMonth / $currentYear", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            // WEEK
            for (week in daysList.chunked(7)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    // WEEKDAY
                    for (day in week) {
                        val isCompleted = completedDays.contains(day)
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (isCompleted) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.background,
                                    shape = CircleShape
                                )
                                .padding(4.dp),
                            contentAlignment = Alignment.Center // text in center
                        ) {
                            Text(
                                text = day.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // LEGEND
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Habit Completed", style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("None Completed", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}