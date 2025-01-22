package at.ccl3.habipet.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    secondary = SecondaryBlue,
    tertiary = TertiaryLilac,
    onPrimary = DarkPurple,
    onSecondary = DarkPurple,
    onTertiary = DarkPurple,

    background = BgBeige,
    onBackground = Color(0xFF1C1B1F),
    surfaceContainer = PastelBlue, // NavBar color
    surface = PaleGray, // default Card color (background)
    onSurface = Color(0xFF1C1B1F), // text color on Cards
    onSurfaceVariant = Color.DarkGray, // text color on NavBar
    error = PastelRed
)

@Composable
fun HabiPetTheme(
    content: @Composable () -> Unit
) {
    // we only use the light color theme :)
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}