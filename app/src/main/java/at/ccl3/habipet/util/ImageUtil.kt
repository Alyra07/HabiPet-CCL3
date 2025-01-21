package at.ccl3.habipet.util

import androidx.annotation.DrawableRes
import at.ccl3.habipet.R

object ImageUtil {
    // Coin Icon (Shop currency)
    @DrawableRes
    fun getCoinIconResource(): Int {
        return R.drawable.coin_64
    }

    // Get correct habit.icon based on string for HabitListItem
    @DrawableRes
    fun getHabitIconResource(icon: String): Int {
        return when (icon) {
            "paw_default" -> R.drawable.habit_ic_default
            "flower" -> R.drawable.habit_vintage_ic
            "smile" -> R.drawable.habit_smile_ic
            "fit" -> R.drawable.habit_fitness_ic
            "heart" -> R.drawable.nav_pet_filled
            else -> R.drawable.habit_ic_default
        }
    }

    // petStats.skin (static png) for customization & ShopScreen
    @DrawableRes
    fun getSkinImageResource(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> R.drawable.skin_whale_default
            "Sushi Wha-Lee" -> R.drawable.skin_whale_sushi
            "Wha-Lee of Doom" -> R.drawable.skin_whale_doom
            "Puffy the Puffer" -> R.drawable.skin_puffy
            "Puffy the Cactus" -> R.drawable.skin_puffy_cactus
            "Poké Puff" -> R.drawable.skin_puffy_pokepuff

            else -> R.drawable.skin_whale_default
        }
    }

    // petStats.habitat Image on PetScreen & ShopScreen
    @DrawableRes
    fun getHabitatImageResource(habitat: String): Int {
        return when (habitat) {
            "Blue Ocean" -> R.drawable.habitat_ocean
            "Cozy Room" -> R.drawable.habitat_room3
            "Green House" -> R.drawable.habitat_forest

            else -> R.drawable.habitat_ocean
        }
    }
}