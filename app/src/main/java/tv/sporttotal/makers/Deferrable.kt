package tv.sporttotal.makers

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class Deferrable {
    fun somethingDeferred() : Deferred<Boolean> {
        return GlobalScope.async {
            delay(100)
            true
        }
    }
}