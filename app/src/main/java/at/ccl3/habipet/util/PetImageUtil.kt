package at.ccl3.habipet.util

import androidx.annotation.DrawableRes
import at.ccl3.habipet.R

object PetImageUtil {

    @DrawableRes
    fun getSkinImageResource(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> R.drawable.habi_whale
            "Sushi Wha-Lee" -> R.drawable.habi_whale_sushi
            "Wha-Lee of Doom" -> R.drawable.habi_whale_doom
            "Puffy the Puffer" -> R.drawable.habi_puffy
            "Puffy the Cactus" -> R.drawable.habi_puffy_cactus
            "Poké Puff" -> R.drawable.habi_puffy_pokepuff
            else -> R.drawable.habi_whale
        }
    }

    @DrawableRes
    fun getHabitatImageResource(habitat: String): Int {
        return when (habitat) {
            "Blue Ocean" -> R.drawable.habitat_ocean
//            "habitat_forest" -> R.drawable.habitat_forest
//            "habitat_desert" -> R.drawable.habitat_desert
            else -> R.drawable.habitat_ocean
        }
    }
}