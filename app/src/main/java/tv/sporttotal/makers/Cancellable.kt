package tv.sporttotal.makers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Cancellable : CoroutineScope {

    override var coroutineContext: CoroutineContext = Dispatchers.Default

    fun somethingAsync() =
        launch {
            somethingSuspended()
        }

    // TODO get out the spy here?
    suspend fun somethingSuspended(): Boolean {
        delay(100)
        return true
    }
}