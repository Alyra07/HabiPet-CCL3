package at.ccl3.habipet.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ccl3.habipet.data.Habit
import at.ccl3.habipet.data.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {
    // StateFlow to hold current state of the list of habits
    private val _allHabits = MutableStateFlow<List<Habit>>(emptyList())
    val allHabits: StateFlow<List<Habit>> = _allHabits

    init {
        // Fetch all habits from the repository and update the state
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

    // READ
    fun getHabitById(habitId: Int): Flow<Habit?> {
        return repository.getHabitById(habitId)
    }

    // UPDATE
    fun update(habit: Habit) = viewModelScope.launch {
        repository.update(habit)
    }

    fun completeHabit(habit: Habit) {
        Log.d("HabitViewModel", "Calling completeHabit for: ${habit.name}")
        viewModelScope.launch {
            repository.completeHabit(habit)
        }
    }

    // DELETE
    fun delete(habit: Habit) = viewModelScope.launch {
        repository.delete(habit)
    }
}