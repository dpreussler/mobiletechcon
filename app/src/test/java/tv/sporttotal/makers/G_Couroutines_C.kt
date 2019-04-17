package tv.sporttotal.makers

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class G_Couroutines_C {


    @Nested
    inner class `I dont know what I am doing` {

        val tested = Cancellable()

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

        val tested = Cancellable()

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

    private val Cancellable.testCoroutineContext: TestCoroutineContext
        get() =  this.coroutineContext as TestCoroutineContext
}