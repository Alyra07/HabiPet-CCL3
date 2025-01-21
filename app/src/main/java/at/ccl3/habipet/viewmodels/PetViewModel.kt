package at.ccl3.habipet.viewmodels

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import at.ccl3.habipet.data.PetStatsRepository
import at.ccl3.habipet.data.PetStats
import at.ccl3.habipet.util.GifUtil
import at.ccl3.habipet.views.ShopItem
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PetViewModel(
    application: Application,
    private val repository: PetStatsRepository
) : AndroidViewModel(application) {

    private val _petStats = MutableStateFlow<PetStats?>(null)
    val petStats: StateFlow<PetStats?> = _petStats

    private val _currentSkin = MutableStateFlow("Wha-Lee Default")
    val currentSkin: StateFlow<String> = _currentSkin

    // READ
    init {
        viewModelScope.launch {
            repository.getPetStats().collect { stats ->
                _petStats.value = stats
                stats.let {
                    _currentSkin.value = it.skin
                }
            }
        }
        preloadAnimations(_currentSkin.value)
    }

    // UPDATE skin & habitat (customize pet)
    fun updatePetSkin(id: Int, newSkin: String) {
        viewModelScope.launch {
            repository.updatePetSkin(id, newSkin)
        }
    }
    fun updatePetHabitat(id: Int, newHabitat: String) {
        viewModelScope.launch {
            repository.updatePetHabitat(id, newHabitat)
        }
    }

    // BUY SHOP ITEM (skins & habitats)
    fun buyShopItem(id: Int, shopItem: ShopItem) {
        viewModelScope.launch {
            val currentStats = repository.getPetStats(id).firstOrNull()
            currentStats?.let { stats ->
                if (stats.coins >= shopItem.price) {
                    val updatedCoins = stats.coins - shopItem.price
                    val updatedStats = when (shopItem.type) {
                        // Update the pet with ownedSkins
                        "skin" -> stats.copy(
                            name = shopItem.tag, // Update the pet's name to the new skin
                            coins = updatedCoins,
                            skin = shopItem.tag,
                            ownedSkins = stats.ownedSkins + shopItem.tag
                        )
                        // Update the user's ownedHabitats
                        "habitat" -> stats.copy(
                            coins = updatedCoins,
                            habitat = shopItem.tag,
                            ownedHabitats = stats.ownedHabitats + shopItem.tag
                        )
                        else -> stats
                    }
                    Log.d("PetViewModel", "Bought ${shopItem.tag} for ${shopItem.price} coins")
                    // Update the pet stats in the database
                    repository.updatePetStats(updatedStats)
                }
            }
        }
    }

    // GIF ANIMATION SECTION (PetScreen only)

    private val _isTapAnimationPlaying = MutableStateFlow(false)
    val isTapAnimationPlaying: StateFlow<Boolean> = _isTapAnimationPlaying

    private var animationJob: Job? = null

    init {
        preloadAnimations(_currentSkin.value)
    }

    // Preload animations for the current skin
    private fun preloadAnimations(skin: String) {
        val context = getApplication<Application>().applicationContext
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(ImageDecoderDecoder.Factory())
                add(GifDecoder.Factory())
            }
            .build()

        viewModelScope.launch(Dispatchers.IO) {
            val idleAnimations = GifUtil.getSkinIdleResource(skin) as List<*>
            idleAnimations.forEach { animationResId ->
                val request = ImageRequest.Builder(context)
                    .data(animationResId)
                    .size(Size.ORIGINAL)
                    .build()
                imageLoader.enqueue(request)
            }

            val tapAnimation = GifUtil.getSkinTappedResource(skin)
            val tapRequest = ImageRequest.Builder(context)
                .data(tapAnimation)
                .size(Size.ORIGINAL)
                .build()
            Log.d("PetViewModel", "Preloading tap animation: $tapAnimation")
            imageLoader.enqueue(tapRequest)
        }
    }

    // Start the idle animation loop
    fun startIdleAnimation(imageView: ImageView, skin: String) {
        animationJob?.cancel()
        animationJob = viewModelScope.launch(Dispatchers.Main) {
            while (true) {
                val idleAnimations = GifUtil.getSkinIdleResource(skin) as List<*>
                val nextAnimation = pickNextAnimation(idleAnimations)

                viewModelScope.launch(Dispatchers.IO) {
                    Glide.with(imageView.context).asGif().load(nextAnimation).preload()
                }

                playAnimation(imageView, nextAnimation)
                delay(GifUtil.getAnimationDuration(nextAnimation))
            }
        }
    }

    // Pick the next animation from the list for different idles
    private fun pickNextAnimation(idleAnimations: List<*>): Any? {
        val randomIndex = (idleAnimations.indices).random()
        return idleAnimations[randomIndex]
    }

    // play tap animation GIF on click
    fun playTapAnimation(imageView: ImageView, skin: String) {
        if (_isTapAnimationPlaying.value) return
        _isTapAnimationPlaying.value = true
        animationJob?.cancel()
        viewModelScope.launch(Dispatchers.Main) {
            val tapAnimation = GifUtil.getSkinTappedResource(skin)
            playAnimation(imageView, tapAnimation)
            delay(GifUtil.getAnimationDuration(tapAnimation))
            _isTapAnimationPlaying.value = false
            startIdleAnimation(imageView, skin)
        }
    }

    // Play the animation using Glide
    private fun playAnimation(imageView: ImageView, animationResId: Any?) {
        Glide.with(imageView.context)
            .asGif()
            .load(animationResId)
            .into(object : CustomTarget<GifDrawable>() {
                override fun onResourceReady(
                    resource: GifDrawable,
                    transition: Transition<in GifDrawable>?
                ) {
                    resource.setLoopCount(1)
                    imageView.setImageDrawable(resource)
                    resource.start()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    imageView.setImageDrawable(placeholder)
                }
            })
    }

    // Update the current skin for animations
    fun updateSkin(newSkin: String) {
        if (_currentSkin.value != newSkin) {
            _currentSkin.value = newSkin
            preloadAnimations(newSkin)
        }
    }
}