package at.ccl3.habipet.util

object HabitUtils {
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
        val oneMinuteInMillis = 60_000L
        return when (repetition) {
            "Daily" -> 86_400_000L
            "Weekly" -> 604_800_000L
            "Monthly" -> 2_592_000_000L
            "Test" -> oneMinuteInMillis
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