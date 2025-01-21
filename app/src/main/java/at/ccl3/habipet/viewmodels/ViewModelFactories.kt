package at.ccl3.habipet.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.ccl3.habipet.data.HabitRepository
import at.ccl3.habipet.data.PetStatsRepository

// Factory for creating HabitViewModel
class HabitViewModelFactory(
    private val habitRepository: HabitRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            HabitViewModel(habitRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

// Factory for creating PetViewModel
class PetViewModelFactory(
    private val application: Application,
    private val repository: PetStatsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}