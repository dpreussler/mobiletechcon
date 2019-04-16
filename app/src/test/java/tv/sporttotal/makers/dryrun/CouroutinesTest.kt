package tv.sporttotal.makers.dryrun

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.test.withTestContext
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Cancellable
import tv.sporttotal.makers.CoroutineDispatchers
import tv.sporttotal.makers.DefaultCoroutineDispatchers
import tv.sporttotal.makers.Deferrable
import tv.sporttotal.makers.Image
import tv.sporttotal.makers.ImageFilter
import tv.sporttotal.makers.ImageLoader
import tv.sporttotal.makers.Suspendable
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class CouroutinesTest : CoroutineScope {

    override val coroutineContext = Dispatchers.Unconfined

    @Test
    fun test() {
        val tested = Suspendable()

        runBlocking {
            tested.somethingSuspended()
        }


        val mocked = mock<Suspendable>()
        runBlocking {
            whenever(mocked.somethingSuspended()).thenReturn(true)
        }

        runBlocking {
            verify(mocked).somethingSuspended()
        }
    }

    @Test
    fun test2() = runBlocking {
        val tested = Suspendable()

        tested.somethingSuspended()

        val mocked = mock<Suspendable>()
        whenever(mocked.somethingSuspended()).thenReturn(true)
        verify(mocked).somethingSuspended()
        Unit
    }

    @Test
    fun test3() = blockingTest {
        val tested = Suspendable()

        tested.somethingSuspended()

        val mocked = mock<Suspendable>()
        whenever(mocked.somethingSuspended()).thenReturn(true)
        verify(mocked).somethingSuspended()
    }


    @Test
    fun test4() = runBlocking<Unit> {
        val tested = Suspendable()

        tested.somethingSuspended()

        val mocked = mock<Suspendable>()
        whenever(mocked.somethingSuspended()).thenReturn(true)
        verify(mocked).somethingSuspended()
    }


    @Test
    fun test5() {
        val tested = Suspendable()

        val mocked = mock<Suspendable> {
            onBlocking { somethingSuspended() } doReturn true
        }

        verifyBlocking(mocked) { somethingSuspended() }
    }


    public fun <T> blockingTest(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T
    ): Unit {
        runBlocking(context, block)
        return Unit
    }

    @Test
    fun test6() {
//        val tested = mock<Deferrable> {
//            on { somethingDeferred() } doReturn GlobalScope.async { true }
//        }

        val tested = mock<Deferrable> {
            on { somethingDeferred() } doReturn CompletableDeferred(true)
        }

        runBlocking {
            tested.somethingDeferred().await() `should be equal to` true

        }
    }

    @Test
    fun test7a() {
        val mock = mock<Suspendable>()
        val tested = Cancellable(mock)
        val testCoroutineContext = TestCoroutineContext()
        tested.coroutineContext = testCoroutineContext
        val job = tested.somethingAsync()
        testCoroutineContext.triggerActions()
        verifyBlocking(mock) { somethingSuspended() }

    }

    @Test
    fun test7b() {
        val mock = mock<Suspendable>()
        val tested = Cancellable(mock)
        val testCoroutineContext = TestCoroutineContext()
        tested.coroutineContext = testCoroutineContext
        val job = tested.somethingAsync()
        job.cancel()
        testCoroutineContext.triggerActions()
        verifyBlocking(mock, never()) { somethingSuspended() }
    }

    @Test
    fun test8b() {
        val suspendable = Suspendable()
        val tested = Cancellable(suspendable)

        val testCoroutineContext = TestCoroutineContext()
        tested.coroutineContext = testCoroutineContext

        val job = tested.somethingAsync()
        testCoroutineContext.triggerActions()
        testCoroutineContext.advanceTimeBy(1, TimeUnit.SECONDS)

        job.isCompleted `should be equal to` true
    }

    class TestDispatchers : CoroutineDispatchers {
        override val default = TestCoroutineContext()
        override val io = TestCoroutineContext()
        override val main = TestCoroutineContext()
    }


    // TODO important stuff here

    @Nested
    inner class UnconfinedAndLaunch : CoroutineScope {

        override val coroutineContext = Dispatchers.Unconfined

        @Test
        fun launching() = withTestContext {
            val loader = mock<ImageLoader>{
                onBlocking { loadImage(any()) } doAnswer { Image(it.arguments.first().toString()) }
            }

            val dispatchers = TestDispatchers()
            val tested = ImageFilter(loader, dispatchers)

            val job = launch{
                val image = tested.loadAndSwitchAndCombine("1", "2")
                image.name `should be equal to` "1:3"
            }
            dispatchers.io.advanceTimeBy(3, TimeUnit.SECONDS)

            job.isCompleted `should be equal to` true

            // THIS SHOULD FAIL BUT WONT
        }
    }


    @Nested
    inner class TestContextAndLaunch : CoroutineScope {

        override val coroutineContext = TestCoroutineContext()

        @Test
        fun launching() = withTestContext {
            val loader = mock<ImageLoader>{
                onBlocking { loadImage(any()) } doAnswer { Image(it.arguments.first().toString()) }
            }

            val dispatchers = TestDispatchers()
            val tested = ImageFilter(loader, dispatchers)

            val job = launch{
                val image = tested.loadAndSwitchAndCombine("1", "2")
                image.name `should be equal to` "1:3"
            }
            coroutineContext.triggerActions()
            dispatchers.io.advanceTimeBy(3, TimeUnit.SECONDS)
            coroutineContext.triggerActions()

            job.isCompleted `should be equal to` true

            coroutineContext.exceptions.forEach { throw it }
        }
    }

    @Nested
    inner class TestContextAndAsync : CoroutineScope {

        override val coroutineContext = TestCoroutineContext()

        @Test
        fun launching() = withTestContext {
            val loader = mock<ImageLoader>{
                onBlocking { loadImage(any()) } doAnswer { Image(it.arguments.first().toString()) }
            }

            val dispatchers = TestDispatchers()
            val tested = ImageFilter(loader, dispatchers)

            val job = async{
                val image = tested.loadAndSwitchAndCombine("1", "2")
                image.name `should be equal to` "1:3"
            }
            coroutineContext.triggerActions()
            dispatchers.io.advanceTimeBy(3, TimeUnit.SECONDS)
            coroutineContext.triggerActions()

            job.isCompleted `should be equal to` true

            job.getCompletionExceptionOrNull()?.let { throw it }
            // or use job.await()
        }
    }

    @Nested
    inner class UnconfinedAndAsync : CoroutineScope {

        override val coroutineContext = Dispatchers.Unconfined

        @Test
        fun launching() = withTestContext {
            val loader = mock<ImageLoader>{
                onBlocking { loadImage(any()) } doAnswer { Image(it.arguments.first().toString()) }
            }

            val dispatchers = TestDispatchers()
            val tested = ImageFilter(loader, dispatchers)

            val job = async{
                val image = tested.loadAndSwitchAndCombine("1", "2")
                image.name `should be equal to` "1:3"
            }
            dispatchers.io.advanceTimeBy(3, TimeUnit.SECONDS)

            job.isCompleted `should be equal to` true

            job.getCompletionExceptionOrNull()?.let { throw it }
            // or use job.await()
        }
    }


    @Test
    fun test9b() = withTestContext{
        val loader = mock<ImageLoader>{
            onBlocking { loadImage(any()) } doAnswer { Image(it.arguments.first().toString()) }
        }

        val dispatchers = TestDispatchers()
        val tested = ImageFilter(loader, dispatchers)

        val job = async{
            val image = tested.loadAndSwitchAndCombine("1", "2")
            image.name `should be equal to` "1:3"
        }
//        coroutineContext.triggerActions()
        dispatchers.io.advanceTimeBy(3, TimeUnit.SECONDS)
//        coroutineContext.triggerActions()

        runBlocking {
            job.await()
        }
    }
}