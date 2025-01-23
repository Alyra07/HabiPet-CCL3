package at.ccl3.habipet.util

import android.util.Log
import androidx.annotation.DrawableRes
import at.ccl3.habipet.R


object GifUtil {
    // Get idle GIF based on skin
    fun getSkinIdleResource(skin: String): Any {
        return when (skin) {
            "Kitty Default" -> listOf(R.drawable.kitty_idle1, R.drawable.kitty_idle2, R.drawable.kitty_idle3)
            "Black Kitty" -> listOf(R.drawable.kitty_black_idle1, R.drawable.kitty_black_idle2, R.drawable.kitty_black_idle3)
            "Pet Cheetah" -> listOf(R.drawable.kitty_cheetah_idle1, R.drawable.kitty_cheetah_idle2, R.drawable.kitty_cheetah_idle3)
            "Wha-Lee Default" -> listOf(R.drawable.whale_idle1, R.drawable.whale_idle2, R.drawable.whale_idle3)
            "Sushi Wha-Lee" -> listOf(R.drawable.whale_sushi_idle1, R.drawable.whale_sushi_idle2, R.drawable.whale_sushi_idle3)
            "Wha-Lee of Doom" -> listOf(R.drawable.whale_doom_idle1, R.drawable.whale_doom_idle2, R.drawable.whale_doom_idle3)
            "Puffy the Puffer" -> listOf(R.drawable.puffy_idle1, R.drawable.puffy_idle2, R.drawable.puffy_idle3)
            "Puffy the Cactus" -> listOf(R.drawable.puffy_cactus_idle1, R.drawable.puffy_cactus_idle2, R.drawable.puffy_cactus_idle3)
            "Poké Puff" -> listOf(R.drawable.puffy_pokepuff_idle1, R.drawable.puffy_pokepuff_idle2, R.drawable.puffy_pokepuff_idle3)
            "Kola the Koala" -> listOf(R.drawable.kola_idle1, R.drawable.kola_idle2, R.drawable.kola_idle3)
            "Dark Kola" -> listOf(R.drawable.kola_dark_idle1, R.drawable.kola_dark_idle2, R.drawable.kola_dark_idle3)
            "Pyjama Kola" -> listOf(R.drawable.kola_pyjama_idle1, R.drawable.kola_pyjama_idle2, R.drawable.kola_pyjama_idle3)

            else -> ImageUtil.getSkinImageResource(skin) // fallback to static image
        }
    }

    // Get tapped GIF based on skin
    @DrawableRes
    fun getSkinTappedResource(skin: String): Int {
        Log.d("GifUtil", "getSkinTappedResource called with skin: $skin")
        return when (skin) {
            "Kitty Default" -> R.drawable.kitty_tap
            "Black Kitty" -> R.drawable.kitty_black_tap
            "Pet Cheetah" -> R.drawable.kitty_cheetah_tap
            "Wha-Lee Default" -> R.drawable.whale_tap
            "Sushi Wha-Lee" -> R.drawable.whale_sushi_tap
            "Wha-Lee of Doom" -> R.drawable.whale_doom_tap
            "Puffy the Puffer" -> R.drawable.puffy_tap
            "Puffy the Cactus" -> R.drawable.puffy_cactus_tap
            "Poké Puff" -> R.drawable.puffy_pokepuff_tap
            "Kola the Koala" -> R.drawable.kola_tap
            "Dark Kola" -> R.drawable.kola_dark_tap
            "Pyjama Kola" -> R.drawable.kola_pyjama_tap

            else -> ImageUtil.getSkinImageResource(skin) // fallback to static image
        }
    }

    // get correct duration for the tap animation GIF
    fun getAnimationDuration(animationResId: Any?): Long {
        Log.d("GifUtil", "getAnimationDuration called with animationResId: $animationResId")
        return when (animationResId) {
            R.drawable.kitty_idle1, R.drawable.kitty_idle2, R.drawable.kitty_idle3 -> 1667L
            R.drawable.kitty_black_idle1, R.drawable.kitty_black_idle2, R.drawable.kitty_black_idle3 -> 1667L
            R.drawable.kitty_cheetah_idle1, R.drawable.kitty_cheetah_idle2, R.drawable.kitty_cheetah_idle3 -> 1667L

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

            R.drawable.kola_idle1, R.drawable.kola_idle2, R.drawable.kola_idle3 -> 1667L
            R.drawable.kola_dark_idle1, R.drawable.kola_dark_idle2, R.drawable.kola_dark_idle3 -> 1667L
            R.drawable.kola_pyjama_idle1, R.drawable.kola_pyjama_idle2, R.drawable.kola_pyjama_idle3 -> 1667L

            else -> 1667L // fallback to default duration (for all other pets)
        }
    }
}