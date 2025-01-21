package at.ccl3.habipet.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.ui.theme.SmokeyGray
import at.ccl3.habipet.util.ImageUtil
import at.ccl3.habipet.viewmodels.PetViewModel
import at.ccl3.habipet.views.ShopItem

@Composable
fun ShopItemCard(
    shopItem: ShopItem,
    viewModel: PetViewModel,
    onBuyClick: (ShopItem) -> Unit,
    isBuyDisabled: Boolean
) {
    val petStats = viewModel.petStats.collectAsState(initial = null).value

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                // ITEM IMAGE
                Image(
                    painter = painterResource(
                        // Get the correct image resource from PetImageUtil based on item type
                        id = (if (shopItem.type == "skin") {
                            ImageUtil.getSkinImageResource(shopItem.tag)
                        } else {
                            ImageUtil.getHabitatImageResource(shopItem.tag)
                        })
                    ),
                    contentDescription = "Shop Item ${shopItem.tag}",
                    modifier = Modifier.size(100.dp)
                )

                // ITEM NAME & PRICE
                Text(text = shopItem.tag, style = MaterialTheme.typography.titleLarge)
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Price: ${if (shopItem.price == 0) "Free" else shopItem.price}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Image( // coin icon
                        painter = painterResource(ImageUtil.getCoinIconResource()),
                        contentDescription = "Coins",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // BUY BUTTON
            Button(
                onClick = { onBuyClick(shopItem) }, // callback to buy the item
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
                border = BorderStroke(1.dp, color = SmokeyGray),
                // Disable the button if you don't have enough coins or item is already owned
                enabled = !isBuyDisabled,
            ) {
                if (petStats != null) {
                    Text( // "Owned" or "Buy"
                        text = if (petStats.ownedSkins.contains(shopItem.tag) || petStats.ownedHabitats.contains(shopItem.tag))
                            "Owned" else "Buy",
                    )
                }
            }
        }
    }
}