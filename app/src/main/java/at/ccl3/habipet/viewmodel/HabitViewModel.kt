package at.ccl3.habipet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.data.HabitRepository
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {
    // READ
    val allHabits: LiveData<List<Habit>> = repository.allHabits.asLiveData()

    // CREATE
    fun insert(habit: Habit) = viewModelScope.launch {
        repository.insert(habit)
    }

    // UPDATE
    fun update(habit: Habit) = viewModelScope.launch {
        repository.update(habit)
    }

    // DELETE
    fun delete(habit: Habit) = viewModelScope.launch {
        repository.delete(habit)
    }
}


