package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetStats(
    @PrimaryKey val id: Int,
    val name: String,
    var level: Int,
    var xp: Int, // needed to level up
    val skin: String,
    val habitat: String, // PetScreen background
    var coins: Int // currency to use in shop
)