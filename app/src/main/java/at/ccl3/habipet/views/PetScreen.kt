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
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.util.PetImageUtil
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

    Column(modifier = Modifier.fillMaxSize(),) {
        // HEADER ROW - include COINS DISPLAY
        TopHeaderBar(
            headingText = "Your Habi",
            navController = navController,
            petViewModel = viewModel
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            // HABI NAME
            Text(
                text = petStats.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // PET STATS SECTION
            Text(text = "Level: ${petStats.level}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "XP: ${petStats.xp}", style = MaterialTheme.typography.bodyLarge)
        }

        // HABIPET IMAGE WITH BACKGROUND DISPLAY
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image( // Habitat background image
                painter = painterResource(id = PetImageUtil.getHabitatImageResource(petStats.habitat)),
                contentDescription = "Habitat Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alpha = 0.9f
            )
            Image( // Pet image
                painter = painterResource(id = PetImageUtil.getSkinImageResource(petStats.skin)),
                contentDescription = "Pet Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(300.dp)
            )
            // CUSTOMIZE BUTTON
            Button(
                onClick = { navController.navigate("skin_selector_menu") },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Customize")
            }
        }
    }
}