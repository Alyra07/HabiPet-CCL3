package at.ccl3.habipet.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.HabitListItem
import at.ccl3.habipet.components.HeaderWithLogo
import at.ccl3.habipet.viewmodels.HabitViewModel

@Composable
fun HabitsScreen(navController: NavController, viewModel: HabitViewModel) {
    // Collect the list of habits from the HabitViewModel
    val habits = viewModel.allHabits.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // HEADER ROW
        HeaderWithLogo(headingText = "Habits", navController = navController)

        // HABIT LIST
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Display all habits from HabitViewModel
            items(habits) { habit ->
                HabitListItem(habit = habit) {
                    val habitId = habit.id
                    // pass habit ID to HabitDetailsView onClick
                    navController.navigate("habitDetails/$habitId")
                }
            }
        }
    }
}