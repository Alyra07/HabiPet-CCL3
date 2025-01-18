package at.ccl3.habipet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.R
import at.ccl3.habipet.viewmodels.PetViewModel

@Composable
fun TopHeaderBar(
    headingText: String,
    navController: NavController?,
    petViewModel: PetViewModel? = null, // pass PetViewModel if coins are needed
    showBackButton: Boolean = false
) {
    val petStats = petViewModel?.petStats?.collectAsState(initial = null)?.value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Space between logo / heading & coins
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // BACK BUTTON (if needed)
            if (showBackButton) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .size(28.dp)
                        .clickable { navController?.popBackStack() }
                )
            } else {
                // LOGO (on most screens)
                Image(
                    painter = painterResource(id = R.drawable.logo_vector),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Fit,
                    alpha = 0.9f
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = headingText, style = MaterialTheme.typography.headlineSmall)
        }

        // COINS DISPLAY
        if (petStats != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.coin_icon),
                    contentDescription = "Coins",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${petStats.coins}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}