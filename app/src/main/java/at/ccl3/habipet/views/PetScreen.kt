package at.ccl3.habipet.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
    // Get the correct GIF resource based on tapped state
    val currentGifResId = remember(isTapped) {
        if (isTapped) GifUtil.getSkinTappedResource(petStats.skin) // tapped
        else GifUtil.getSkinIdleResource(petStats.skin) // default: idle
    }
    // image
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())
                add(GifDecoder.Factory())
            }
            .build()
    }

    LaunchedEffect(isTapped) {
        if (isTapped) {
            // Reset to idle after playing tapped animation once
            delay(1900)
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
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(currentGifResId)
                        .size(Size.ORIGINAL)
                        .build(),
                    imageLoader = imageLoader
                ),
                contentDescription = "Pet Animation",
                modifier = Modifier
                    .size(380.dp)
                    .align(Alignment.Center)
                    .clickable {
                        if (!isTapped) {
                            isTapped = true
                        }
                    }
                    .animateContentSize()
            )

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