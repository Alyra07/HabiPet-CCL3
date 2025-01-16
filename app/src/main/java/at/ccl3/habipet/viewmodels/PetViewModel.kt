package at.ccl3.habipet.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ccl3.habipet.data.PetStatsRepository
import at.ccl3.habipet.data.PetStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    // UPDATE XP
    fun updatePetXP(id: Int, xp: Int) {
        viewModelScope.launch {
            repository.updatePetXP(id, xp)
            Log.d("PetViewModel", "Updated Pet $id with: $xp XP")
        }
    }

    // UPDATE Coins
    fun updatePetCoins(id: Int, coins: Int) {
        viewModelScope.launch {
            repository.updatePetCoins(id, coins)
            Log.d("PetViewModel", "Updated Pet $id with: $coins Coins")
        }
    }
}

