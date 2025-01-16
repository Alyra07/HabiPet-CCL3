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
                petStatsDao.insertPetStats(
                    PetStats(id = 1, name = "Whale", level = 1, xp = 0, skin = "default", habitat = "habitat_ocean", coins = 0)
                )
            }
        }
    }

    // UPDATE XP
    suspend fun updatePetXP(id: Int, xp: Int) {
        val updatedPetStats = petStatsDao.getPetStats(id).firstOrNull()?.copy(xp = xp)
        updatedPetStats?.let {
            petStatsDao.updatePetStats(it)  // pass the updated PetStats to DAO
        }
        Log.d("PetStatsRepository", "Updated Pet $id - it now has $xp XP")

        // update pet level and reset if XP reaches 1000
        if (xp >= 1000) {
            updatedPetStats?.let {
                it.level++
                it.xp -= 1000
                petStatsDao.updatePetStats(it)

                Log.d("PetStatsRepository", "LEVEL UP! Level ${it.level} !!")
            }
        }
    }

    // UPDATE COINS
    suspend fun updatePetCoins(id: Int, coins: Int) {
        val updatedPetStats = petStatsDao.getPetStats(id).firstOrNull()?.copy(coins = coins)
        updatedPetStats?.let {
            val coinsToAward = 50 // Adjust coin reward here :)
            it.coins += coinsToAward

            petStatsDao.updatePetStats(it) // pass the updated PetStats to DAO
            Log.d("PetStatsRepository", "Updated Coins - New Balance: ${updatedPetStats.coins} Coins")
        }
    }
}
