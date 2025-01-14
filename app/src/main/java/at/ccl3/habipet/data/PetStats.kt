package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetStats(
    @PrimaryKey val id: Int,
    val name: String,
    val level: Int,
    val xp: Int,
    val skin: String,
    val habitat: String,
)