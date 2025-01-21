package at.ccl3.habipet.views

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ccl3.habipet.components.PetLevelBar
import at.ccl3.habipet.components.TopHeaderBar
import at.ccl3.habipet.ui.theme.SmokeyGray
import at.ccl3.habipet.util.GifUtil
import at.ccl3.habipet.util.ImageUtil
import at.ccl3.habipet.viewmodels.PetViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.delay

@Composable
fun PetScreen(navController: NavController, viewModel: PetViewModel) {
    val petStats = viewModel.petStats.collectAsState(initial = null).value
    val context = LocalContext.current
    if (petStats == null) {
        Text(text = "ERROR: No pet stats found...", modifier = Modifier.padding(16.dp))
        return
    }

    var isTapped by remember { mutableStateOf(false) }
    // Preload both idle and tapped animations
    val idleGifResId = GifUtil.getSkinIdleResource(petStats.skin)
    val tappedGifResId = GifUtil.getSkinTappedResource(petStats.skin)

    // Current GIF resource to display
    val currentGifResId by rememberUpdatedState(if (isTapped) tappedGifResId else idleGifResId)

    // Image loader for handling GIFs
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())
                add(GifDecoder.Factory())
            }
            .build()
    }

    // Handle tap and transition to idle animation
    LaunchedEffect(isTapped) {
        if (isTapped) {
            val duration = GifUtil.getTapAnimationDuration(petStats.skin)
            delay(duration.toLong())
            isTapped = false
        }
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
                // display the correct habitat background image that fills the screen
                painter = painterResource(id = ImageUtil.getHabitatImageResource(petStats.habitat)),
                contentDescription = "Habitat Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alpha = 0.9f
            )

            // PET IMAGE as GIF
            Crossfade(
                targetState = currentGifResId,
                modifier = Modifier.align(Alignment.Center)
            ) { gifResId ->
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context)
                            .data(gifResId)
                            .size(Size.ORIGINAL)
                            .build(),
                        imageLoader = imageLoader
                    ),
                    contentDescription = "Pet Animation",
                    modifier = Modifier
                        .size(380.dp)
                        .align(Alignment.Center)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                if (!isTapped) {
                                    isTapped = true
                                }
                            }
                        }
                )
            }

            // CUSTOMIZE BUTTON
            Button(
                // navigate to CustomizePetScreen when clicked
                onClick = { navController.navigate("customize_pet") },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                border = BorderStroke(1.dp, color = SmokeyGray)
            ) {
                Text(text = "Customize")
            }
        }
    }
}