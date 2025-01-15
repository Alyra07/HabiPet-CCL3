package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val repetition: String, // how often habit should be completed
    val streak: Int = 0, // how many times habit has been completed in a row
    val icon: String,
    val color: String,
    val lastCompleted: Long = 0L // to track the last completion time
)