package at.ccl3.habipet.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.ui.theme.*
import at.ccl3.habipet.util.ImageUtil

@Composable
fun IconAndColorSelector(
    selectedIcon: String,
    selectedColor: String,
    onIconSelected: (String) -> Unit,
    onColorSelected: (String) -> Unit
) {
    // Predefined icons and colors
    val icons = listOf("paw_default", "flower", "smile", "fit", "heart")
    val colorMap = mapOf(
        "Default" to SmokeyGray,
        "Red" to PastelRed,
        "Purple" to MaterialTheme.colorScheme.tertiary,
        "Blue" to MaterialTheme.colorScheme.secondary,
        "Green" to AccentGreen,
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // CHOOSE ICON
        Text(text = "Select Icon", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            icons.forEach { icon ->
                val iconRes = ImageUtil.getHabitIconResource(icon)
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onIconSelected(icon) }
                        .background(
                            color = if (icon == selectedIcon) MaterialTheme.colorScheme.background
                            else MaterialTheme.colorScheme.surface,
                            shape = CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (icon == selectedIcon) MaterialTheme.colorScheme.onPrimary else SmokeyGray,
                            shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = icon,
                        tint = if (icon == selectedIcon) MaterialTheme.colorScheme.onPrimary else SmokeyGray
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // CHOOSE COLOR
        Text(text = "Select Color", style = MaterialTheme.typography.titleSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            colorMap.entries.forEach { (colorName, colorValue) ->
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onColorSelected(colorName) }
                        .border(
                            width = 2.dp,
                            color = if (colorName == selectedColor) MaterialTheme.colorScheme.onPrimary else SmokeyGray,
                            shape = CircleShape)
                        .background(
                            color = colorValue,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}