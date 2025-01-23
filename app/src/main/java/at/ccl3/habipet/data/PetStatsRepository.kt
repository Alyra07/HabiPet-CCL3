package at.ccl3.habipet.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart

class PetStatsRepository(private val petStatsDao: PetStatsDao) {
    // READ
    fun getPetStats(id: Int = 1): Flow<PetStats> {
        return petStatsDao.getPetStats(id).onStart {
            // Emit default petStats if none exist in the database (Starter Pet)
            if (petStatsDao.getPetStats(id).firstOrNull() == null) {
                val defaultPetStats = PetStats(id = 1, name = "Kitty", level = 1, xp = 0)
                petStatsDao.insertPetStats(defaultPetStats)
                Log.d("PetStatsRepository", "Inserted default Pet Stats: $defaultPetStats")
            }
        }
    }

    // UPDATE
    suspend fun updatePetStats(petStats: PetStats) {
        petStatsDao.updatePetStats(petStats)
        Log.d("PetStatsRepository", "Updated Pet Stats: $petStats")
    }

    // UPDATE XP (for completing habits)
    suspend fun updatePetXP(id: Int, xp: Int) {
        val updatedPetStats = petStatsDao.getPetStats(id).firstOrNull()?.copy(xp = xp)
        updatedPetStats?.let {
            petStatsDao.updatePetStats(it)
        }
        Log.d("PetStatsRepository", "Updated Pet $id - it now has $xp XP")

        // LEVEL UP and reset if XP reaches 1000
        if (xp >= 1000) {
            updatedPetStats?.let {
                it.level++
                it.xp -= 1000
                petStatsDao.updatePetStats(it)

                Log.d("PetStatsRepository", "LEVEL UP! Level ${it.level} !!")
            }
        }
    }

    // UPDATE COINS if a streakGoal (in HabitRepository) is reached
    suspend fun addCoins(id: Int, coins: Int) {
        val updatedPetStats = petStatsDao.getPetStats(id).firstOrNull()?.copy(coins = coins)
        updatedPetStats?.let {
            petStatsDao.updatePetStats(it)
            Log.d("PetStatsRepository", "Updated Coins - New Balance: ${updatedPetStats.coins} Coins")
        }
    }

    // UPDATE SKIN
    suspend fun updatePetSkin(id: Int, newSkin: String) {
        val currentStats = petStatsDao.getPetStats(id).firstOrNull()
        currentStats?.let {
            val updatedStats = it.copy(
                // rename the pet to its skin name, but remove Default string
                name = if (newSkin.contains("Default")) newSkin.substringBefore(" Default")
                else newSkin,
                skin = newSkin // set new skin
            )
            Log.d("PetStatsRepository", "Updated Pet Skin: $newSkin")
            petStatsDao.updatePetStats(updatedStats)
        }
    }

    // UPDATE HABITAT
    suspend fun updatePetHabitat(id: Int, newHabitat: String) {
        val currentStats = petStatsDao.getPetStats(id).firstOrNull()
        currentStats?.let {
            val updatedStats = it.copy(habitat = newHabitat)
            Log.d("PetStatsRepository", "Updated Pet Habitat: $newHabitat")
            petStatsDao.updatePetStats(updatedStats) // set new habitat
        }
    }
}