package tv.sporttotal.android.mobiletechcon

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext

class SomethiingCancelled {

    class Someone : CoroutineScope {

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

    @Nested
    inner class `I dont know what I am doing` {

        val tested = Someone()

        @Test
        fun `check for running`() {

            val someone = spy(tested)
            val job = someone.somethingAsync()

            verifyBlocking(someone) { somethingSuspended() }
        }

        @Test
        fun `check for cancellation`() {

            val someone = spy(tested)
            val job = someone.somethingAsync()
            job.cancel()

            // TODO WTF
            verifyBlocking(someone, never()) { somethingSuspended() }
        }
    }

    @Nested
    inner class `Maybe know` {

        val tested = Someone()

        @BeforeEach
        fun setup() {
            tested.coroutineContext = TestCoroutineContext()

        }

        @Test
        fun `check for running`() {

            val someone = spy(tested)
            val job = someone.somethingAsync()

            someone.testCoroutineContext.triggerActions()

            verifyBlocking(someone) { somethingSuspended() }
        }

        @Test
        fun `check for cancellation`() {

            val someone = spy(tested)
            val job = someone.somethingAsync()
            job.cancel()

            someone.testCoroutineContext.triggerActions()

            verifyBlocking(someone, never()) { somethingSuspended() }
        }
    }

    private val Someone.testCoroutineContext: TestCoroutineContext
        get() =  this.coroutineContext as TestCoroutineContext
}