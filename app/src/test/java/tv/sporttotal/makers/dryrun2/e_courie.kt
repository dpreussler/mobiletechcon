package tv.sporttotal.makers.dryrun2

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.CoroutineDispatchers
import tv.sporttotal.makers.LiveGame
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.TestGame

class courie {

    @Nested
    inner class `Given something suspended` {

        @Test
        fun `call suspended`() {
            val tested = mock<LiveGame>()

            runBlocking {
                whenever(tested.broadcast()).thenReturn( true)
            }
        }

        @Test
        fun `call suspended better`() = blockingTest {
            val tested = mock<LiveGame>()

            whenever(tested.broadcast()).thenReturn( true)
        }

        @Test
        fun `call suspended much better`() = runBlocking<Unit>{
            val tested = mock<LiveGame>()

            whenever(tested.broadcast()).thenReturn( true)

            verify(tested).broadcast()
        }

        @Test
        fun `call suspended mockito`() {
            val tested = mock<LiveGame> {
                onBlocking{ broadcast() } doReturn true
            }

            verifyBlocking(tested) { broadcast() }
        }
    }

    @Nested
    inner class `Given something deferred` {

        @Test
        fun test() {
            val tested = mock<LiveGame> {
                onBlocking { startBroadcasting() } doReturn CompletableDeferred(true)
            }

            runBlocking {
                tested.startBroadcasting().await() `should be equal to` true
            }
        }
    }

    @Nested
    inner class `Given something cancellable` {

        @Test
        fun test() {
            val game = mock<LiveGame>()

            val tested = TestGame(game)
            val testJob = tested.testSignal()

            testJob.cancel()

            verifyBlocking(game, never()) { broadcast() }
            testJob.isCompleted `should be equal to` true
        }

        @Test
        fun test2() {
            val game = mock<LiveGame>()

            val testCoroutineContext = TestCoroutineContext()

            val tested = TestGame(game, testCoroutineContext)
            val testJob = tested.testSignal()

            testJob.cancel()
            testCoroutineContext.triggerActions()

            verifyBlocking(game, never()) { broadcast() }
        }
    }

    @Nested
    inner class `Given something timeable` {
        @Test
        fun test2() {
            val game = mock<LiveGame>()

            val testCoroutineContext = TestCoroutineContext()

            val tested = TestGame(game, testCoroutineContext)
            val testJob = tested.testSignal()

            testJob.cancel()
            testCoroutineContext.triggerActions()

            verifyBlocking(game, never()) { broadcast() }
        }
    }

    private fun blockingTest(block: suspend () -> Unit) {
        runBlocking { block() }
    }
}