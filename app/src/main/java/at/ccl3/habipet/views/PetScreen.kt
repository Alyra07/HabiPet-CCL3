package at.ccl3.habipet.views

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.PetLevelBar
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.ui.theme.SmokeyGray
import at.ccl3.habipet.util.PetImageUtil
import at.ccl3.habipet.viewmodels.PetViewModel

@Composable
fun PetScreen(navController: NavController, viewModel: PetViewModel) {
    val petStats = viewModel.petStats.collectAsState(initial = null).value

    if (petStats == null) {
        Text(text = "ERROR: No pet stats found...", modifier = Modifier.padding(16.dp))
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW with COINS
        TopHeaderBar(
            headingText = "Your Habi",
            navController = navController,
            petViewModel = viewModel
        )

        Column( // PET STATS
            modifier = Modifier.fillMaxWidth()
        ) {
            // PetLevelBar shows xp & level progress
            PetLevelBar(petStats)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // BACKGROUND IMAGE (habitat)
            Image(
                // display the correct habitat background image
                painter = painterResource(id = PetImageUtil.getHabitatImageResource(petStats.habitat)),
                contentDescription = "Habitat Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth, // background bounds
                alpha = 0.9f
            )
            // PET IMAGE
            Image(
                // display the currently selected skin image
                painter = painterResource(id = PetImageUtil.getSkinImageResource(petStats.skin)),
                contentDescription = "Pet Image",
                modifier = Modifier
                    .align(Alignment.Center) // center of habitat
                    .size(400.dp)
                    .padding(top = 90.dp) // offset
            )
            // CUSTOMIZE BUTTON
            Button(
                onClick = { navController.navigate("skin_selector_menu") },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                border = BorderStroke(1.dp, color = SmokeyGray)
            ) {
                Text(text = "Customize")
            }
        }
    }
}