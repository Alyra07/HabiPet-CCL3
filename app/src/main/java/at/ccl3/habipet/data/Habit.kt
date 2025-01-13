package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val repetition: String,
    val icon: String,
    val color: String
)