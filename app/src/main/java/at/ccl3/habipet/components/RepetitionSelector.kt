package at.ccl3.habipet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.ui.theme.SmokeyGray

// Component to select repetition options in HabitEditView & AddHabitScreen
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RepetitionSelector(currentRepetition: String, onRepetitionChange: (String) -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Repetition", style = MaterialTheme.typography.titleSmall)

        FlowRow(
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 4, // 4 options per row
            overflow = FlowRowOverflow.Clip
        ) {
            listOf("Daily", "Weekly", "Monthly", "Test").forEach { option ->
                Button(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    onClick = { onRepetitionChange(option) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentRepetition == option) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.surface,
                        contentColor = if (currentRepetition == option) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = BorderStroke(
                        width = 2.dp,
                        color = if (currentRepetition == option) MaterialTheme.colorScheme.onPrimary else SmokeyGray
                    )
                ) {
                    Text(text = option)
                }
            }
        }
    }
}