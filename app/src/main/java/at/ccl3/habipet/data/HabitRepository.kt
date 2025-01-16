package at.ccl3.habipet.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class HabitRepository(
    private val habitDao: HabitDao, // For main habit CRUD operations
    private val petStatsRepository: PetStatsRepository // for updating petStats (level, coins)
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

    // Complete habit after countdown and update the habit & petStats states
    suspend fun completeHabit(habit: Habit) {
        val currentPetStats = petStatsRepository.getPetStats().first()

        val currentTime = System.currentTimeMillis()
        val oneMinuteInMillis = 60 * 1000

        // Define streak goal based on habit repetition
        val streakGoal = when (habit.repetition) {
            "Daily" -> 7
            "Weekly" -> 4
            "Monthly" -> 2
            else -> 2 // "Test" case
        }

        if (currentTime - habit.lastCompleted >= oneMinuteInMillis) {
            // Habit can be completed
            val updatedHabit = habit.copy(
                streak = if (habit.streak + 1 >= streakGoal) {
                    0 // Reset streak if goal is met
                } else {
                    habit.streak + 1 // Increment streak
                },
                lastCompleted = currentTime
            )
            habitDao.update(updatedHabit)
            // Award XP for habit completion
            petStatsRepository.updatePetXP(currentPetStats.id, currentPetStats.xp + 100)

            // Check if streak goal is met before awarding extra COINS
            if (updatedHabit.streak == 0 && habit.streak + 1 >= streakGoal) {
                petStatsRepository.updatePetCoins(currentPetStats.id, currentPetStats.coins)
            }

            Log.d("HabitRepository", "Habit completed: $updatedHabit , new time completed: $currentTime")
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