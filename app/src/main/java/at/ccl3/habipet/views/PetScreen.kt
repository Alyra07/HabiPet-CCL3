package at.ccl3.habipet.views

import android.widget.ImageView
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import at.ccl3.habipet.components.PetLevelBar
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.ui.theme.SmokeyGray
import at.ccl3.habipet.util.ImageUtil
import at.ccl3.habipet.viewmodels.PetViewModel

@Composable
fun PetScreen(navController: NavController, viewModel: PetViewModel) {
    val petStats = viewModel.petStats.collectAsState(initial = null).value
    val isTapAnimationPlaying = viewModel.isTapAnimationPlaying.collectAsState().value
    val currentSkin = viewModel.currentSkin.collectAsState().value

    if (petStats == null) {
        Text(text = "ERROR: No pet stats found...", modifier = Modifier.padding(16.dp))
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // HEADER ROW with COINS
        TopHeaderBar(
            headingText = "Your Habi",
            navController = navController,
            petViewModel = viewModel
        )

        Column( // PET STATS
            modifier = Modifier.fillMaxWidth()
        ) {
            // PetLevelBar shows xp & level progress
            PetLevelBar(petStats)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            // BACKGROUND IMAGE (habitat)
            Image(
                painter = painterResource(id = ImageUtil.getHabitatImageResource(petStats.habitat)),
                contentDescription = "Habitat Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alpha = 0.9f
            )

            // PET IMAGE as GIF
            var imageView: ImageView? = null
            AndroidView(
                modifier = Modifier
                    .size(380.dp)
                    .align(Alignment.Center)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            imageView?.let { viewModel.playTapAnimation(it, currentSkin) }
                        }
                    },
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_INSIDE
                        imageView = this
                    }
                },
                update = { view ->
                    if (isTapAnimationPlaying) {
                        viewModel.playTapAnimation(view, currentSkin)
                    } else {
                        viewModel.startIdleAnimation(view, currentSkin)
                    }
                }
            )

            // When currentSkin changes, restart the idle animation
            LaunchedEffect(petStats) {
                petStats.let {
                    viewModel.updateSkin(it.skin)
                }
            }

            // CUSTOMIZE BUTTON
            Button(
                onClick = { navController.navigate("customize_pet") },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                border = BorderStroke(2.dp, color = SmokeyGray)
            ) {
                Text(text = "Customize")
            }
        }
    }
}