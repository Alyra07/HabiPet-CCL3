package at.ccl3.habipet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    // READ
    @Query("SELECT * FROM habit_table ORDER BY name ASC")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE id = :habitId")
    fun getHabitById(habitId: Int): Flow<Habit?>

    // UPDATE
    @Update
    suspend fun update(habit: Habit)

    // DELETE
    @Delete
    suspend fun delete(habit: Habit)
}