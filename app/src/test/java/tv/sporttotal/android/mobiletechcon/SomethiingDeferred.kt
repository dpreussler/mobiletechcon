package tv.sporttotal.android.mobiletechcon

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.withTestContext
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class SomethiingDeferred {

    class Someone {
        fun somethingSuspended() :Deferred<Boolean> {
            return GlobalScope.async {
                delay(100)
                true
            }
        }
    }

    @Test
    fun `the boring way`() {

        val result = Someone().somethingSuspended()

        runBlocking {
            result.await() `should be equal to` true
        }
    }

    @Test
    fun `the less boring way`() = runBlocking {

        val result = Someone().somethingSuspended()

        runBlocking {
            result.await() `should be equal to` true
        }

        Unit // << junit 5 issue
    }

    @Test
    fun `the less boring way 2`() = blockingTest {

        val result = Someone().somethingSuspended()

        runBlocking {
            result.await() `should be equal to` true
        }
    }

    @Test
    fun `the less boring way 3`() = withTestContext {

        val result = Someone().somethingSuspended()

        runBlocking {
            result.await() `should be equal to` true
        }
    }

    //

    @Test
    fun `mock the wrong way`() {
        val mocked = mock<Someone>{
            on {somethingSuspended()} doReturn CompletableDeferred(true)
        }

        runBlocking {
            mocked.somethingSuspended()
        }

        // havent checked result
    }

    @Test
    fun `mock mock way`() {
        val deferred = mock<Deferred<Boolean>>()
        val mocked = mock<Someone>{
            on {somethingSuspended()} doReturn deferred
        }

        runBlocking {
            mocked.somethingSuspended().await()
        }

        // havent checked result
        verifyBlocking(deferred) { await() }
    }

    fun <T> blockingTest(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T) =
        Unit.also { runBlocking(context, block) }
}