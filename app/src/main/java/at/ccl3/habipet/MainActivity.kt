package at.ccl3.habipet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import at.ccl3.habipet.data.HabiPetDatabase
import at.ccl3.habipet.data.HabitRepository
import at.ccl3.habipet.views.AddHabitScreen
import at.ccl3.habipet.views.HabitsScreen
import at.ccl3.habipet.views.HomeScreen
import at.ccl3.habipet.views.PetScreen
import at.ccl3.habipet.ui.theme.HabiPetTheme
import at.ccl3.habipet.viewmodels.HabitViewModel
import androidx.compose.ui.Modifier
import at.ccl3.habipet.components.BottomNavBar
import at.ccl3.habipet.data.PetStatsRepository
import at.ccl3.habipet.views.HabitDetailsView
import at.ccl3.habipet.views.HabitEditView
import at.ccl3.habipet.views.ShopScreen
import at.ccl3.habipet.viewmodels.HabitViewModelFactory
import at.ccl3.habipet.viewmodels.PetViewModel
import at.ccl3.habipet.viewmodels.PetViewModelFactory
import at.ccl3.habipet.views.SkinSelectorMenu

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
            // SkinSelectorMenu (from PetScreen)
            composable("skin_selector_menu") { SkinSelectorMenu(navController, petViewModel) }
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