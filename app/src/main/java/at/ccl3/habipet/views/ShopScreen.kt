package at.ccl3.habipet.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.ShopItemCard
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.viewmodels.PetViewModel

// SINGLE SHOP ITEM
data class ShopItem(val tag: String, val price: Int, val type: String)

@Composable
fun ShopScreen(navController: NavController, petViewModel: PetViewModel) {
    val petStats = petViewModel.petStats.collectAsState(initial = null).value

    // ALL SHOP ITEMS
    val shopItems = listOf(
        ShopItem(tag = "Sushi Wha-Lee", price = 10, type = "skin"),
        ShopItem(tag = "Wha-Lee of Doom", price = 20, type = "skin"),
        ShopItem(tag = "Puffy the Puffer", price = 20, type = "skin"),
        ShopItem(tag = "Puffy the Cactus", price = 20, type = "skin"),
        ShopItem(tag = "Poké Puff", price = 100, type = "skin"),

        ShopItem(tag = "Cozy Room", price = 100, type = "habitat"),
        ShopItem(tag = "Green House", price = 50, type = "habitat"),
        ShopItem(tag = "Blue Ocean", price = 0, type = "habitat"),
        // More items... :)
    )

    // Filter shop items by type
    val skinItems = shopItems.filter { it.type == "skin" }
    val habitatItems = shopItems.filter { it.type == "habitat" }

    Column(modifier = Modifier.fillMaxSize()) {
        TopHeaderBar(headingText = "Shop", navController = navController, petViewModel = petViewModel)

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // SKINS SECTION
            item {
                Text(
                    text = "Habi Skins",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(skinItems) { item ->
                // Check if skin is owned and if it's affordable
                val isOwned = petStats?.ownedSkins?.contains(item.tag) ?: false
                val isAffordable = (petStats?.coins ?: 0) >= item.price
                // Single skin Shop Item Card
                ShopItemCard(
                    shopItem = item,
                    viewModel = petViewModel,
                    onBuyClick = { shopItem ->
                        petStats?.let { stats ->
                            if (isAffordable && !isOwned) {
                                petViewModel.buyShopItem(stats.id, shopItem)
                            }
                        }
                    },
                    // disable buy button if owned or not affordable
                    isBuyDisabled = isOwned || !isAffordable
                )
            }

            // HABITATS SECTION
            item {
                Text(
                    text = "Habitats",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(habitatItems) { item ->
                // Check if habitat is owned and if it's affordable
                val isOwned = petStats?.ownedHabitats?.contains(item.tag) ?: false
                val isAffordable = (petStats?.coins ?: 0) >= item.price
                // Single habitat Shop Item Card
                ShopItemCard(
                    shopItem = item,
                    viewModel = petViewModel,
                    onBuyClick = { shopItem ->
                        petStats?.let { stats ->
                            if (isAffordable && !isOwned) {
                                petViewModel.buyShopItem(stats.id, shopItem)
                            }
                        }
                    },
                    // disable buy button if owned or not affordable
                    isBuyDisabled = isOwned || !isAffordable
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp)) // spacing bottom screen
            }
        }
    }
}