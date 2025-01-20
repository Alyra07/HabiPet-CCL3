package at.ccl3.habipet.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.util.ImageUtil
import at.ccl3.habipet.viewmodels.PetViewModel
import kotlinx.coroutines.launch

@Composable
fun CustomizePetScreen(navController: NavController, viewModel: PetViewModel) {
    val petStats = viewModel.petStats.collectAsState(initial = null).value
    val coroutineScope = rememberCoroutineScope()

    if (petStats == null) {
        Text(text = "No pet stats found...", modifier = Modifier.padding(16.dp))
        return
    }

    Column (modifier = Modifier.fillMaxSize()) {
        // HEADER ROW with back button
        TopHeaderBar(
            headingText = "Customize your HabiPet",
            navController = navController,
            showBackButton = true
        )

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(text = "Select Skin", style = MaterialTheme.typography.titleMedium)
            }
            item { // LIST OF OWNED SKINS
                petStats.ownedSkins.forEach { skinTag ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    viewModel.updatePetSkin(petStats.id, skinTag)
                                    navController.popBackStack() // Return to PetScreen after skin selection
                                }
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            // Get the correct image resource from PetImageUtil based on skin tag
                            painter = painterResource(id = ImageUtil.getSkinImageResource(skinTag)),
                            contentDescription = skinTag,
                            modifier = Modifier.size(90.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = skinTag, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Select Habitat", style = MaterialTheme.typography.titleMedium)
            }
            item { // LIST OF OWNED HABITATS
                petStats.ownedHabitats.forEach { habitatItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    viewModel.updatePetHabitat(petStats.id, habitatItem)
                                    navController.popBackStack() // return to PetScreen after selection
                                }
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            // Get the correct image resource from PetImageUtil based on habitat chosen
                            painter = painterResource(id = ImageUtil.getHabitatImageResource(habitatItem)),
                            contentDescription = habitatItem,
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = habitatItem, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}