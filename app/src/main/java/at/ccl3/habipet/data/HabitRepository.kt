package at.ccl3.habipet.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
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

    // DELETE
    suspend fun delete(habit: Habit) {
        habitDao.delete(habit)
        Log.d("HabitRepository", "Habit deleted: $habit")
    }
}
