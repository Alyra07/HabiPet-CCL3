package at.ccl3.habipet.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PetStatsDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetStats(petStats: PetStats)

    // READ
    @Query("SELECT * FROM PetStats WHERE id = :id")
    fun getPetStats(id: Int = 1): Flow<PetStats>

    // UPDATE
    @Update
    suspend fun updatePetXP(petStats: PetStats)
}