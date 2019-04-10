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

///**
// * A coroutine dispatcher that is not confined to any specific thread.
// * It executes initial continuation of the coroutine _immediately_ in the current call-frame
// * and lets the coroutine resume in whatever thread that is used by the corresponding suspending function, without
// * mandating any specific threading policy.
// * **Note: use with extreme caution, not for general code**.
// *
// * Note, that if you need your coroutine to be confined to a particular thread or a thread-pool after resumption,
// * but still want to execute it in the current call-frame until its first suspension, then you can use
// * an optional [CoroutineStart] parameter in coroutine builders like
// * [launch][CoroutineScope.launch] and [async][CoroutineScope.async] setting it to the
// * the value of [CoroutineStart.UNDISPATCHED].
// *
// * **Note: This is an experimental api.**
// * Semantics, order of execution, and particular implementation details of this dispatcher may change in the future.
// */
//@JvmStatic
//@ExperimentalCoroutinesApi
//public actual val Unconfined: CoroutineDispatcher = kotlinx.coroutines.Unconfined
//