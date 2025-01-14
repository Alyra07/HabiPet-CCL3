package at.ccl3.habipet.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ccl3.habipet.data.HabitRepository
import at.ccl3.habipet.data.PetStatsRepository

class HabiPetViewModelFactory(
    private val habitRepository: HabitRepository, // for HabitViewModel
    private val petStatsRepository: PetStatsRepository // for PetViewModel

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the modelClass is assignable from HabitViewModel or PetViewModel
        return when {
            modelClass.isAssignableFrom(HabitViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                HabitViewModel(habitRepository) as T
            }
            modelClass.isAssignableFrom(PetViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                PetViewModel(petStatsRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}