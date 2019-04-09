package tv.sporttotal.makers

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


class G_Couroutines_A {

    @Test
    fun `the boring way`() {

        runBlocking {
            Suspendable().somethingSuspended()
        }

        val mocked = mock<Suspendable>()
        runBlocking {
            whenever(mocked.somethingSuspended()).doReturn(false)
        }

        runBlocking {
            mocked.somethingSuspended()
        }
        runBlocking {
            verify(mocked).somethingSuspended()
        }
    }

    @Test
    fun `the less boring way`() = runBlocking {

        val mocked = mock<Suspendable>()
        whenever(mocked.somethingSuspended()).doReturn(false)

        mocked.somethingSuspended()
        verify(mocked).somethingSuspended()

        Unit // << junit 5 issue
    }

    @Test
    fun `the less boring way 2`() = blockingTest {

        val mocked = mock<Suspendable>()
        whenever(mocked.somethingSuspended()).doReturn(false)

        mocked.somethingSuspended()
        verify(mocked).somethingSuspended()
    }

    @Test
    fun `the less boring way 3`() = runBlocking<Unit> {

        val mocked = mock<Suspendable>()
        whenever(mocked.somethingSuspended()).doReturn(false)

        mocked.somethingSuspended()
        verify(mocked).somethingSuspended()
    }

    @Test
    fun `the easier way`() {
        val mocked = mock<Suspendable> {
            onBlocking {
                somethingSuspended()
            } doReturn false
        }

        runBlocking { mocked.somethingSuspended() }
        verifyBlocking(mocked) {somethingSuspended()}
    }


    fun <T> blockingTest(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T) =
        Unit.also { runBlocking(context, block) }
}