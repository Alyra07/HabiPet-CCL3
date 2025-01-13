package at.ccl3.habipet.data

import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    // CREATE
    suspend fun insert(habit: Habit) {
        habitDao.insert(habit)
    }

    // UPDATE
    suspend fun update(habit: Habit) {
        habitDao.update(habit)
    }

    // DELETE
    suspend fun delete(habit: Habit) {
        habitDao.delete(habit)
    }
}
