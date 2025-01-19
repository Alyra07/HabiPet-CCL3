package at.ccl3.habipet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import at.ccl3.habipet.ui.theme.HabiPetTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import at.ccl3.habipet.data.*
import at.ccl3.habipet.views.*
import at.ccl3.habipet.viewmodels.*
import at.ccl3.habipet.components.*

class MainActivity : ComponentActivity() {
    // Initialize HabitViewModel
    private val habitViewModel: HabitViewModel by viewModels {
        HabitViewModelFactory(
            HabitRepository(
                habitDao = HabiPetDatabase.getDatabase(applicationContext).habitDao(),
                petStatsRepository = PetStatsRepository(HabiPetDatabase.getDatabase(applicationContext).petStatsDao())
            )
        )
    }
    // Initialize PetViewModel
    private val petViewModel: PetViewModel by viewModels {
        PetViewModelFactory(
            PetStatsRepository(HabiPetDatabase.getDatabase(applicationContext).petStatsDao())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabiPetTheme {
                HabiPetApp(habitViewModel, petViewModel)
            }
        }
    }
}

@Composable
fun HabiPetApp(habitViewModel: HabitViewModel, petViewModel: PetViewModel) {
    val navController = rememberNavController()

    // APP NAVIGATION
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // MAIN NAVIGATION ROUTES
            composable("home") { HomeScreen(navController, habitViewModel, petViewModel) }
            composable("pet") { PetScreen(navController, petViewModel) }
            composable("add_habit") { AddHabitScreen(navController, habitViewModel) }
            composable("habits") { HabitsScreen(navController, habitViewModel, petViewModel) }
            composable("shop") { ShopScreen(navController, petViewModel) }
            // CustomizePetScreen (from PetScreen)
            composable("skin_selector_menu") { CustomizePetScreen(navController, petViewModel) }
            // HabitDetailsView with habitId when clicking on HabitListItem
            composable("habitDetails/{habitId}") { backStackEntry ->
                val habitId = backStackEntry.arguments?.getString("habitId")?.toInt() ?: 0
                HabitDetailsView(navController, habitViewModel, habitId)
            }
            // HabitEditView with habitId reached from HabitDetailsView
            composable("editHabit/{habitId}") { backStackEntry ->
                val habitId = backStackEntry.arguments?.getString("habitId")?.toInt() ?: 0
                HabitEditView(navController, habitViewModel, habitId)
            }
        }
    }
}