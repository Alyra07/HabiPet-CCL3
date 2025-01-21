package at.ccl3.habipet.util

import android.util.Log
import androidx.annotation.DrawableRes
import at.ccl3.habipet.R


object GifUtil {
    // Get idle GIF based on skin
    fun getSkinIdleResource(skin: String): Any {
        return when (skin) {
            "Wha-Lee Default" -> listOf(R.drawable.whale_idle1, R.drawable.whale_idle2, R.drawable.whale_idle3)
            "Sushi Wha-Lee" -> listOf(R.drawable.whale_sushi_idle1, R.drawable.whale_sushi_idle2, R.drawable.whale_sushi_idle3)
            "Wha-Lee of Doom" -> listOf(R.drawable.whale_doom_idle1, R.drawable.whale_doom_idle2, R.drawable.whale_doom_idle3)
            "Puffy the Puffer" -> listOf(R.drawable.puffy_idle1, R.drawable.puffy_idle2, R.drawable.puffy_idle3)
            "Puffy the Cactus" -> listOf(R.drawable.puffy_cactus_idle1, R.drawable.puffy_cactus_idle2, R.drawable.puffy_cactus_idle3)
            "Poké Puff" -> listOf(R.drawable.puffy_pokepuff_idle1, R.drawable.puffy_pokepuff_idle2, R.drawable.puffy_pokepuff_idle3)

            else -> ImageUtil.getSkinImageResource(skin) // fallback to static image
        }
    }

    // Get tapped GIF based on skin
    @DrawableRes
    fun getSkinTappedResource(skin: String): Int {
        Log.d("GifUtil", "getSkinTappedResource called with skin: $skin")
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
    fun getAnimationDuration(animationResId: Any?): Long {
        Log.d("GifUtil", "getAnimationDuration called with animationResId: $animationResId")
        return when (animationResId) {
            R.drawable.whale_idle1, R.drawable.whale_idle2, R.drawable.whale_idle3 -> 1667L
            R.drawable.whale_sushi_idle1, R.drawable.whale_sushi_idle2, R.drawable.whale_sushi_idle3 -> 1667L
            R.drawable.whale_doom_idle1, R.drawable.whale_doom_idle2, R.drawable.whale_doom_idle3 -> 1667L
            R.drawable.whale_tap -> 1867L
            R.drawable.whale_sushi_tap -> 1867L
            R.drawable.whale_doom_tap -> 1867L

            R.drawable.puffy_idle1, R.drawable.puffy_idle2, R.drawable.puffy_idle3 -> 1867L
            R.drawable.puffy_cactus_idle1, R.drawable.puffy_cactus_idle2, R.drawable.puffy_cactus_idle3 -> 1867L
            R.drawable.puffy_pokepuff_idle1, R.drawable.puffy_pokepuff_idle2, R.drawable.puffy_pokepuff_idle3 -> 1867L
            R.drawable.puffy_tap -> 2000L
            R.drawable.puffy_cactus_tap -> 2000L
            R.drawable.puffy_pokepuff_tap -> 2000L

            else -> 2000L
        }
    }
}