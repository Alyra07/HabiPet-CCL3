package at.ccl3.habipet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.ccl3.habipet.util.Converters

@Database(entities = [Habit::class, PetStats::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // register TypeConverters util
abstract class HabiPetDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao // for habit related CRUD operations
    abstract fun petStatsDao(): PetStatsDao // for updating petStats

    companion object {
        @Volatile
        private var INSTANCE: HabiPetDatabase? = null

        fun getDatabase(context: Context): HabiPetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabiPetDatabase::class.java,
                    "habit_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

