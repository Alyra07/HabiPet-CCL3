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
import at.ccl3.habipet.data.HabitDatabase
import at.ccl3.habipet.data.HabitRepository
import at.ccl3.habipet.screens.AddHabitScreen
import at.ccl3.habipet.screens.HabitsScreen
import at.ccl3.habipet.screens.HomeScreen
import at.ccl3.habipet.screens.PetScreen
import at.ccl3.habipet.ui.theme.HabiPetTheme
import at.ccl3.habipet.viewmodel.HabitViewModel
import at.ccl3.habipet.viewmodel.HabitViewModelFactory
import androidx.compose.ui.Modifier
import at.ccl3.habipet.components.BottomNavBar
import at.ccl3.habipet.screens.ShopScreen

class MainActivity : ComponentActivity() {
    private val habitViewModel: HabitViewModel by viewModels {
        HabitViewModelFactory(
            HabitRepository(HabitDatabase.getDatabase(applicationContext).habitDao())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabiPetTheme {
                HabiPetApp(habitViewModel)
            }
        }
    }
}

@Composable
fun HabiPetApp(habitViewModel: HabitViewModel) {
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
            // ROUTES
            composable("home") { HomeScreen(navController, habitViewModel) }
            composable("pet") { PetScreen(navController, habitViewModel) }
            composable("add_habit") { AddHabitScreen(navController, habitViewModel) }
            composable("habits") { HabitsScreen(navController, habitViewModel) }
            composable("shop") { ShopScreen(navController, habitViewModel) }
        }
    }
}