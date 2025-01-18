package at.ccl3.habipet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.R
import at.ccl3.habipet.util.PetImageUtil
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
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
                            PetImageUtil.getSkinImageResource(shopItem.tag)
                        } else {
                            PetImageUtil.getHabitatImageResource(shopItem.tag)
                        })
                    ),
                    contentDescription = "Shop Item ${shopItem.tag}",
                    modifier = Modifier.size(100.dp)
                )

                // ITEM NAME & PRICE
                Text(text = shopItem.tag, style = MaterialTheme.typography.titleLarge)
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Price: ${shopItem.price}", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(4.dp))
                    Image( // coin icon
                        painter = painterResource(id = R.drawable.coin_icon),
                        contentDescription = "Coins",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // BUY BUTTON
            Button(
                onClick = { onBuyClick(shopItem) },
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
    Spacer(modifier = Modifier.height(8.dp)) // spacing between cards
}
