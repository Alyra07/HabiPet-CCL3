package at.ccl3.habipet.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PetStats(
    @PrimaryKey val id: Int,
    var name: String = "Kitty",
    var level: Int,
    var xp: Int, // needed to level up
    val skin: String = "Kitty Default", // current pet skin chosen
    val habitat: String = "Cozy Room", // current PetScreen background

    var coins: Int = 0, // currency to use in shop
    val ownedSkins: List<String> = listOf("Kitty Default"), // owned shop items
    val ownedHabitats: List<String> = listOf("Cozy Room"), // choose habitat
)
