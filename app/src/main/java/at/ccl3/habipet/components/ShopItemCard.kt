package at.ccl3.habipet.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import at.ccl3.habipet.views.ShopItem

@Composable
fun ShopItemCard(
    shopItem: ShopItem,
    onBuyClick: (ShopItem) -> Unit,
    painter: Int,
    isBuyDisabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                    painter = painterResource(id = painter),
                    contentDescription = "Shop Item ${shopItem.name}",
                    modifier = Modifier.size(100.dp)
                )
                // ITEM NAME & PRICE
                Text(text = shopItem.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Price: ${shopItem.price} coins", style = MaterialTheme.typography.bodyLarge)
            }
            // BUY BUTTON
            Button(
                onClick = { onBuyClick(shopItem) },
                // Disable the button if you don't have enough coins or item is already owned
                enabled = !isBuyDisabled
            ) {
                Text(text = "Buy")
            }
        }
    }
}
