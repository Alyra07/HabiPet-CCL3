package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetStats(
    @PrimaryKey val id: Int,
    var name: String,
    var level: Int,
    var xp: Int, // needed to level up
    val skin: String = "Wha-Lee Default", // current pet skin chosen
    val habitat: String = "Blue Ocean", // current PetScreen background

    var coins: Int, // currency to use in shop
    val ownedSkins: List<String> = listOf("Wha-Lee Default"), // owned shop items
    val ownedHabitats: List<String> = listOf("Blue Ocean"), // choose habitat
)
