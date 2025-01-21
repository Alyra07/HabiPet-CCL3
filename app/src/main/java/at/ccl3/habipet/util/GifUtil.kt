package at.ccl3.habipet.util

import androidx.annotation.DrawableRes
import at.ccl3.habipet.R


object GifUtil {
    // Get idle GIF based on skin
    @DrawableRes
    fun getSkinIdleResource(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> R.drawable.whale_idle1
            "Puffy the Puffer" -> R.drawable.puffy_idle1
            else -> R.drawable.skin_whale_default // Default fallback
        }
    }

    // Get tapped GIF based on skin
    @DrawableRes
    fun getSkinTappedResource(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> R.drawable.whale_tap
            "Puffy the Puffer" -> R.drawable.puffy_tap
            else -> R.drawable.skin_whale_default // Default fallback
        }
    }

    fun getTapAnimationDuration(skin: String): Int {
        return when (skin) {
            "Wha-Lee Default" -> 1640
            "Puffy the Puffer" -> 2060
            else -> 1900 // Default fallback
        }
    }
}