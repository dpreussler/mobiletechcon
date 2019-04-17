package tv.sporttotal.makers

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

interface ImageLoader {
    suspend fun loadImage(image: String): Image
}

class ImageFilter(
    val imageLoader: ImageLoader,
    val dispatchers: CoroutineDispatchers = DefaultCoroutineDispatchers()) {



    suspend fun loadAndCombine(name1: String, name2: String): Image =
        coroutineScope {
            val deferred1 = async { imageLoader.loadImage(name1) }
            val deferred2 = async { imageLoader.loadImage(name2) }
            combineImages(deferred1.await(), deferred2.await())
        }





    suspend fun loadAndSwitchAndCombine(name1: String, name2: String): Image =
        coroutineScope {
            val deferred1 = async { imageLoader.loadImage(name1) }
            val deferred2 = async { imageLoader.loadImage(name2) }
            val images = awaitAll(deferred1, deferred2)

            withContext(dispatchers.io) {
                combineImages(images.first(), images.last())
            }
        }





    private suspend fun combineImages(name1: Image, name2: Image) : Image {
        delay(3000)
        return Image("${name1.name}:${name2.name}")
    }

}
