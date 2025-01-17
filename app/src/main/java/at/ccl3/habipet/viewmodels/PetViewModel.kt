package at.ccl3.habipet.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ccl3.habipet.data.PetStatsRepository
import at.ccl3.habipet.data.PetStats
import at.ccl3.habipet.views.ShopItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PetViewModel(private val repository: PetStatsRepository) : ViewModel() {
    private val _petStats = MutableStateFlow<PetStats?>(null)
    val petStats: StateFlow<PetStats?> = _petStats

    // READ
    init {
        viewModelScope.launch {
            repository.getPetStats().collect { stats ->
                _petStats.value = stats
            }
        }
    }

    // UPDATE COINS & PET STATS (skins & habitats)
    fun buyShopItem(id: Int, shopItem: ShopItem) {
        viewModelScope.launch {
            val currentStats = repository.getPetStats(id).firstOrNull()
            currentStats?.let { stats ->
                if (stats.coins >= shopItem.price) {
                    val updatedCoins = stats.coins - shopItem.price
                    val updatedStats = when (shopItem.type) {
                        // Update the pet with ownedSkins
                        "skin" -> stats.copy(
                            // Update pet name if it's Puffy the Puffer :)
                            name = if (shopItem.tag.contains("puffy")) "Puffy the Puffer" else stats.name,
                            ownedPets = if (shopItem.tag.contains("puffy")) stats.ownedPets + "puffy" else stats.ownedPets,
                            coins = updatedCoins,
                            skin = shopItem.tag,
                            ownedSkins = stats.ownedSkins + shopItem.tag
                        )
                        // Update the user's ownedHabitats
                        "habitat" -> stats.copy(
                            coins = updatedCoins,
                            habitat = shopItem.tag,
                            ownedHabitats = stats.ownedHabitats + shopItem.tag
                        )
                        else -> stats
                    }
                    Log.d("PetViewModel", "Bought ${shopItem.name} for ${shopItem.price} coins")
                    // Update the pet stats in the database
                    repository.updatePetStats(updatedStats)
                }
            }
        }
    }
}

