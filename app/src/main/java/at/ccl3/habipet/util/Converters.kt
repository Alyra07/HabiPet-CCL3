package at.ccl3.habipet.util

import androidx.room.TypeConverter

class Converters {
    // Convert List<String> to a single String
    @TypeConverter
    fun fromListToString(value: List<String>): String {
        return value.joinToString(",") // Convert list to comma-separated string
    }

    // Convert String to List<String>
    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return value.split(",") // Convert back to list
    }
}