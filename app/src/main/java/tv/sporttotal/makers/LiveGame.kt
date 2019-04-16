package tv.sporttotal.makers

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class LiveGame(val match: Match) {

    suspend fun broadcast(): Boolean {
        delay(90000)
        return true
    }

    suspend fun startBroadcasting(): Deferred<Boolean> {
        return GlobalScope.async {
            broadcast()
        }
    }
}