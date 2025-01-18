package at.ccl3.habipet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
        // SKINS
        ShopItem(tag = "Sushi Wha-Lee", price = 10, type = "skin"),
        ShopItem(tag = "Wha-Lee of Doom", price = 20, type = "skin"),
        ShopItem(tag = "Puffy the Puffer", price = 20, type = "skin"),
        ShopItem(tag = "Puffy the Cactus", price = 20, type = "skin"),
        ShopItem(tag = "Poké Puff", price = 100, type = "skin"),
        // HABITATS
        ShopItem(tag = "Blue Ocean", price = 100, type = "habitat"),
        // More items... :)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopHeaderBar(headingText = "Shop", navController = navController, petViewModel = petViewModel)

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(shopItems) { item ->
                // Check if item is owned or affordable
                val isOwned = when (item.type) {
                    "skin" -> petStats?.ownedSkins?.contains(item.tag) ?: false
                    "habitat" -> petStats?.ownedHabitats?.contains(item.tag) ?: false
                    else -> false
                }
                val isAffordable = when (item.type) {
                    "skin" -> (petStats?.coins ?: 0) >= item.price
                    "habitat" -> (petStats?.coins ?: 0) >= item.price
                    else -> false
                }
                // Single ShopItemCard with buy button
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
                    // disable buy button if item is already owned or not affordable
                    isBuyDisabled = isOwned || !isAffordable
                )
            }
        }
    }
}