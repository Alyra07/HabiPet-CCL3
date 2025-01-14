package at.ccl3.habipet.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart

class PetStatsRepository(private val petStatsDao: PetStatsDao) {

    // READ
    fun getPetStats(id: Int = 1): Flow<PetStats> {
        return petStatsDao.getPetStats(id).onStart {
            // Emit default petStats if none exist in the database
            if (petStatsDao.getPetStats(id).firstOrNull() == null) {
                petStatsDao.insertPetStats(
                    PetStats(id = 1, name = "Whale", level = 1, xp = 0, skin = "default", habitat = "habitat_ocean")
                )
            }
        }
    }

    // UPDATE
    suspend fun updatePetXP(id: Int, xp: Int) {
        val updatedPetStats = petStatsDao.getPetStats(id).firstOrNull()?.copy(xp = xp)
        updatedPetStats?.let {
            petStatsDao.updatePetXP(it)  // Pass the updated PetStats to the DAO
        }
    }
}
