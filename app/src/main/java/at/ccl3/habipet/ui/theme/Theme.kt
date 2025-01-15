package at.ccl3.habipet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = PrimaryYellow,
    secondary = SecondaryBlue,
    tertiary = AccentLilac,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    secondary = SecondaryBlue,
    tertiary = AccentLilac,

    // Other default colors to override
    background = BgBeige,
    onBackground = Color(0xFF1C1B1F),
    surfaceContainer = PastelBlue, // NavBar color
    surface = PaleGray, // Card color (HabitListItem & HabitCompleteCard)
    onSurface = Color(0xFF1C1B1F),
    onPrimary = DarkPurple,
    onSecondary = DarkPurple,
    onTertiary = DarkPurple,
)

@Composable
fun HabiPetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}