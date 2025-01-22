package at.ccl3.habipet.util

import androidx.compose.ui.graphics.Color
import at.ccl3.habipet.ui.theme.AccentLilac
import at.ccl3.habipet.ui.theme.PaleGray
import at.ccl3.habipet.ui.theme.PastelRed
import at.ccl3.habipet.ui.theme.PrimaryYellow
import at.ccl3.habipet.ui.theme.SecondaryBlue
import at.ccl3.habipet.ui.theme.SmokeyGray

object HabitUtils {
    // Get right habit.color based on string
    fun getHabitColor(color: String): Color {
        return when (color) {
            "Default" -> SmokeyGray
            "Yellow" -> PrimaryYellow
            "Blue" -> SecondaryBlue
            "Purple" -> AccentLilac
            "Red" -> PastelRed
            else -> PaleGray // fallback to bg color
        }
    }

    // Set STREAK GOAL based on repetition type
    // (you get coins for completing streak goal)
    fun getStreakGoal(repetition: String): Int {
        return when (repetition) {
            "Daily" -> 7
            "Weekly" -> 4
            "Monthly" -> 2
            else -> 2 // "Test" case
        }
    }
    // Set XP you get for completing a habit
    fun getXpToAward(repetition: String): Int {
        return when (repetition) {
            "Daily" -> 100
            "Weekly" -> 200
            "Monthly" -> 300
            else -> 400 // "Test" case
        }
    }
    // Set COINS you get for completing streak goal
    fun getCoinsToAward(repetition: String): Int {
        return when (repetition) {
            "Daily" -> 100
            "Weekly" -> 200
            "Monthly" -> 300
            else -> 100 // "Test" case
        }
    }
    // Get DURATION in milliseconds based on repetition type
    fun getDurationMillis(repetition: String): Long {
        val oneSecondInMillis = 1_000L
        return when (repetition) {
            "Daily" -> 86_400_000L
            "Weekly" -> 604_800_000L
            "Monthly" -> 2_592_000_000L
            "Test" -> oneSecondInMillis * 10 // "Test" case 10 seconds
            else -> Long.MAX_VALUE
        }
    }
    // Calculate TIME PROGRESS based on repetition type
    fun calculateTimeProgress(
        timeSinceLastCompletion: Long,
        repetition: String
    ): Triple<Float, Int, Any> {
        val oneMinuteInMillis = 60_000L
        return when (repetition) {
            "Daily" -> Triple(
                timeSinceLastCompletion.toFloat() / 86_400_000,
                7,
                86_400_000
            )
            "Weekly" -> Triple(
                timeSinceLastCompletion.toFloat() / 604_800_000,
                4,
                604_800_000
            )
            "Monthly" -> Triple(
                timeSinceLastCompletion.toFloat() / 2_592_000_000,
                2,
                2_592_000_000
            )
            "Test" -> Triple(
                timeSinceLastCompletion.toFloat() / oneMinuteInMillis,
                2,
                oneMinuteInMillis
            )
            else -> Triple(0f, 1, 1)
        }.let { Triple(it.first.coerceIn(0f, 1f), it.second, it.third) }
    }
}