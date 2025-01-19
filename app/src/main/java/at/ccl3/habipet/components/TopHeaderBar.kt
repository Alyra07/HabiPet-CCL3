package at.ccl3.habipet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
                    painter = painterResource(id = R.drawable.logo_64),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = headingText, style = MaterialTheme.typography.headlineSmall)
        }

        // COINS DISPLAY
        if (petStats != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${petStats.coins}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Image( // coin icon
                    painter = painterResource(id = R.drawable.coin_32),
                    contentDescription = "Coins",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}