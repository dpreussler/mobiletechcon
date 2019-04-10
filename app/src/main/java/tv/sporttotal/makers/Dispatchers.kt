package tv.sporttotal.makers

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {
    val default: CoroutineContext
    val io: CoroutineContext
    val main: CoroutineContext
}
class DefaultCoroutineDispatchers : CoroutineDispatchers {
    override val default get() = Dispatchers.Default
    override val io get() = Dispatchers.IO
    override val main get() = Dispatchers.Main
}