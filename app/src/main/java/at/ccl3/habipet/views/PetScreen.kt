package at.ccl3.habipet.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.R
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.viewmodels.PetViewModel

@Composable
fun PetScreen(navController: NavController, viewModel: PetViewModel) {
    // Collect the petStats from PetViewModel
    val petStats = viewModel.petStats.collectAsState(initial = null).value

    // If petStats is null, display empty state
    if (petStats == null) {
        Text(text = "No pet stats found yet...", modifier = Modifier.padding(16.dp))
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW - include COINS DISPLAY
        TopHeaderBar(
            headingText = "Your HabiPet",
            navController = navController,
            petViewModel = viewModel
        )

        // PET STATS DISPLAY
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // PET STATS SECTION
            Text(
                text = "Pet Name: ${petStats.name}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Pet Stats:", style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Level: ${petStats.level}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "XP: ${petStats.xp}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Skin: ${petStats.skin}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Owned Skins: ${petStats.ownedSkins.joinToString(", ")}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Habitat: ${petStats.habitat}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Box( // BACKGROUND & PET IMAGE
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image( // Set background image (habitat)
                painter = painterResource(
                    // Set background image based on habitat chosen
                    id = when (petStats.habitat) {
                        "habitat_ocean" -> R.drawable.habitat_ocean
//                        "habitat_forest" -> R.drawable.habitat_forest
//                        "habitat_desert" -> R.drawable.habitat_desert
                        else -> R.drawable.habitat_ocean
                    }
                ),
                contentDescription = "Habitat Background",
                modifier = Modifier.fillMaxSize(), // image covers the full Box
                contentScale = ContentScale.FillBounds, // scales image to fill the Box
                alpha = 0.9f // background transparency
            )

            Image( // Pet image on top of background (animation later!!)
                painter = painterResource(
                    // Set pet image based on skin chosen
                    id = when (petStats.skin) {
                        "whale_default" -> R.drawable.habi_whale
                        "whale_sushi" -> R.drawable.habi_whale_sushi
                        "whale_doom" -> R.drawable.habi_whale_doom
                        else -> R.drawable.habi_whale

                    }),
                contentDescription = "Pet Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(300.dp)
            )
        }
    }
}