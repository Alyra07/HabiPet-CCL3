package at.ccl3.habipet.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class HabitRepository(
    private val habitDao: HabitDao, // For main habit CRUD operations
    private val petStatsRepository: PetStatsRepository // for updating petStats (xp)
) {
    // get Flow of all habits in the database
    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    // CREATE
    suspend fun insert(habit: Habit) {
        habitDao.insert(habit)
        Log.d("HabitRepository", "Habit inserted: $habit")
    }

    // READ
    fun getHabitById(habitId: Int): Flow<Habit?> {
        return habitDao.getHabitById(habitId)
    }

    // UPDATE
    suspend fun update(habit: Habit) {
        habitDao.update(habit)
        Log.d("HabitRepository", "Habit updated: $habit")
    }

    // Complete habit after countdown and update the state
    suspend fun completeHabit(habit: Habit) {
        val currentTime = System.currentTimeMillis()
        val oneMinuteInMillis = 60 * 1000 // 1 minute in milliseconds

        if (currentTime - habit.lastCompleted >= oneMinuteInMillis) {
            // Habit can be completed
            val updatedHabit = habit.copy(
                streak = habit.streak + 1,
                lastCompleted = currentTime
            )
            habitDao.update(updatedHabit)

            // Award XP to the pet
            val currentPetStats = petStatsRepository.getPetStats().first()
            petStatsRepository.updatePetXP(currentPetStats.id, currentPetStats.xp + 100)

            Log.d("HabitRepository", "Habit completed: $habit , new time completed: $currentTime")
            Log.d("HabitRepository", "XP awarded to pet, new XP: ${currentPetStats.xp + 100}")
        } else {
            Log.d(
                "HabitRepository",
                "Habit not completed. Time since last completion: ${currentTime - habit.lastCompleted}"
            )
        }
    }

    // DELETE
    suspend fun delete(habit: Habit) {
        habitDao.delete(habit)
        Log.d("HabitRepository", "Habit deleted: $habit")
    }
}