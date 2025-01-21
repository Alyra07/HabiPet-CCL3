package at.ccl3.habipet.util

import androidx.annotation.DrawableRes
import at.ccl3.habipet.R


object GifUtil {
    // Get idle GIF based on skin
    @DrawableRes
    fun getSkinIdleResource(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> R.drawable.whale_idle1
            "Sushi Wha-Lee" -> R.drawable.whale_sushi_idle1
            "Wha-Lee of Doom" -> R.drawable.whale_doom_idle1
            "Puffy the Puffer" -> R.drawable.puffy_idle1
            "Puffy the Cactus" -> R.drawable.puffy_cactus_idle1
            "Poké Puff" -> R.drawable.puffy_pokepuff_idle1

            else -> ImageUtil.getSkinImageResource(skin) // fallback to static image
        }
    }

    // Get tapped GIF based on skin
    @DrawableRes
    fun getSkinTappedResource(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> R.drawable.whale_tap
            "Sushi Wha-Lee" -> R.drawable.whale_sushi_tap
            "Wha-Lee of Doom" -> R.drawable.whale_doom_tap
            "Puffy the Puffer" -> R.drawable.puffy_tap
            "Puffy the Cactus" -> R.drawable.puffy_cactus_tap
            "Poké Puff" -> R.drawable.puffy_pokepuff_tap

            else -> ImageUtil.getSkinImageResource(skin) // fallback to static image
        }
    }

    // get correct duration for the tap animation GIF
    fun getTapAnimationDuration(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> 1640
            "Sushi Wha-Lee" -> 1640
            "Wha-Lee of Doom" -> 1640
            "Puffy the Puffer" -> 2060
            "Puffy the Cactus" -> 2060
            "Poké Puff" -> 2060

            else -> 1800
        }
    }
}