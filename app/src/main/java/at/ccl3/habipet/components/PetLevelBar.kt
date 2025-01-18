package at.ccl3.habipet.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.data.PetStats

@Composable
fun PetLevelBar(petStats: PetStats) {
    // Level up after 1000 XP
    val progress = (petStats.xp % 1000) / 1000f
    val currentLevel = petStats.level
    // val nextLevel = currentLevel + 1

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text( // Pet name
                text = petStats.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LinearProgressIndicator(
                // XP Progress bar
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.background,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                gapSize = 0.dp,
            )
            Row( // Current Level & XP display
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Level $currentLevel", style = MaterialTheme.typography.titleMedium)
                Text(text = "${petStats.xp}/1000 XP", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
