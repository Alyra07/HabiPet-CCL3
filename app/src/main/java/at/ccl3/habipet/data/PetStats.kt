package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetStats(
    @PrimaryKey val id: Int,
    var name: String,
    val ownedPets: List<String> = listOf("whale"),
    var level: Int,
    var xp: Int, // needed to level up
    val skin: String, // current pet skin chosen
    val ownedSkins: List<String> = listOf("whale_default"), // owned shop items
    val habitat: String, // current PetScreen background
    val ownedHabitats: List<String> = listOf("habitat_ocean"), // choose habitat
    var coins: Int // currency to use in shop
)
