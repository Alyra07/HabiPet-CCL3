package at.ccl3.habipet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.util.HabitUtils
import at.ccl3.habipet.util.ImageUtil

@Composable
fun HabitListItem(habit: Habit, onClick: () -> Unit) {
    val habitColor = HabitUtils.getHabitColor(habit.color)
    val habitIcon = ImageUtil.getHabitIconResource(habit.icon)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // handle navigation to HabitDetailsView
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box( // habit.icon & habit.color
                modifier = Modifier
                    .size(48.dp)
                    .background(color = habitColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = habitIcon),
                    contentDescription = habit.icon,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column { // habit.name, habit.description & habit.repetition
                Text(text = habit.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = habit.description, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Repetition: ${habit.repetition}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}