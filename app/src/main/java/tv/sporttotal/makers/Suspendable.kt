package tv.sporttotal.makers

import kotlinx.coroutines.delay

class Suspendable {
    suspend fun somethingSuspended() :Boolean {
        delay(100)
        return true
    }
}