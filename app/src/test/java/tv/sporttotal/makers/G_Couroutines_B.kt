package tv.sporttotal.makers

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test


class G_Couroutines_B {

    @Test
    fun `mock it`() = runBlocking<Unit>{
        val mocked = mock<Deferrable> {
            on {
                somethingDeferred()
            } doReturn GlobalScope.async{ true }
        }

        mocked.somethingDeferred().await() `should be equal to` true
    }

    @Test
    fun `better`() = runBlocking<Unit>{
        val mocked = mock<Deferrable> {
            on {
                somethingDeferred()
            } doReturn CompletableDeferred(true)
        }

        mocked.somethingDeferred().await() `should be equal to` true
    }

    // It is better than writing async because it doesn't launch anything concurrently
    // (otherwise your test timings may become unstable) and gives you more control over what happens in what order.
}