package at.ccl3.habipet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.viewmodels.PetViewModel

@Composable
fun ShopScreen(navController: NavController, petViewModel: PetViewModel) {
    // Collect pet stats (skin, coins etc.) from PetViewModel
    val petStats = petViewModel.petStats.collectAsState(initial = null).value

    Column (modifier = Modifier.fillMaxSize()) {
        // HEADER ROW
        TopHeaderBar(headingText = "Shop", navController = navController, petViewModel = petViewModel)

        // SHOP CONTENT
        LazyColumn (
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text(text = "Welcome to HabiPet Shop!")
            }
            // Add more UI elements
        }
    }
}