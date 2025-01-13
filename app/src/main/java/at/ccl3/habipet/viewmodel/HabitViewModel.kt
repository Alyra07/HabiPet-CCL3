package at.ccl3.habipet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.data.HabitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {
    // Use StateFlow to store habits
    private val _allHabits = MutableStateFlow<List<Habit>>(emptyList())
    val allHabits: StateFlow<List<Habit>> = _allHabits

    init {
        // READ
        viewModelScope.launch {
            repository.allHabits.collect { habits ->
                _allHabits.value = habits
            }
        }
    }

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
