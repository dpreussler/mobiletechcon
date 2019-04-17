package tv.sporttotal.makers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface LiveGame {
    suspend fun broadcast(): Boolean
    suspend fun startBroadcasting(): Deferred<Boolean>
}

class TestGame(
    val liveGame: LiveGame,
    override val coroutineContext: CoroutineContext = Dispatchers.Default): CoroutineScope {

    fun testSignal() =
        launch {
            liveGame.broadcast()
        }

}

class NinetyMinuteGame(val match: Match): LiveGame {

    override suspend fun broadcast(): Boolean {
        delay(90000)
        return true
    }

    override suspend fun startBroadcasting(): Deferred<Boolean> {
        return GlobalScope.async {
            broadcast()
        }
    }
}