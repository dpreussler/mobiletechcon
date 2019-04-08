package tv.sporttotal.makers

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

data class Image(val name: String)

class ImageFilter(val imageLoader: ImageLoader) {

    suspend fun loadAndCombine(name1: String, name2: String): Image =
        coroutineScope {
            val deferred1 = async { imageLoader.loadImage(name1) }
            val deferred2 = async { imageLoader.loadImage(name2) }
            combineImages(deferred1.await(), deferred2.await())
        }

    private fun combineImages(name1: Image, name2: Image) = Image("${name1.name}:${name2.name}")

}

interface ImageLoader {
    suspend fun loadImage(image: String): Image
}