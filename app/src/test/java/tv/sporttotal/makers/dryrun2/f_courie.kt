package tv.sporttotal.makers.dryrun2

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.withTestContext
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.CoroutineDispatchers
import tv.sporttotal.makers.DefaultCoroutineDispatchers
import tv.sporttotal.makers.Image
import tv.sporttotal.makers.ImageFilter
import tv.sporttotal.makers.ImageLoader
import tv.sporttotal.makers.LiveGame
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.TestCoroutineDispatchers
import tv.sporttotal.makers.TestGame
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

@Suppress("EXPERIMENTAL_API_USAGE")
class courie2 : CoroutineScope {

    override val coroutineContext = TestCoroutineContext()

    val instantLoader = object : ImageLoader {
        override suspend fun loadImage(image: String) = Image(image)
    }
    val tested = ImageFilter(instantLoader)


    @Test
    fun `test loader`() {

        val job = launch {
            tested.loadAndCombine("1", "2").name `should be equal to` "1:3"
        }

        // async would not even work

        coroutineContext.advanceTimeBy(3, TimeUnit.SECONDS)
        job.isCompleted `should be equal to` true

        coroutineContext.exceptions.forEach { throw it }
//            coroutineContext.assertAllUnhandledExceptions { false }
    }


    @Test
    fun `with custom loaders`() {

        val delayedLoader = spy(object : ImageLoader {
            override suspend fun loadImage(image: String): Image {
                delay(1000)
                return Image(image)
            }
        })

        val tested = ImageFilter(delayedLoader)

        val job = launch {
            tested.loadAndCombine("1", "2").name `should be equal to` "1:2"
        }

        // async would not even work

        coroutineContext.advanceTimeBy(1, TimeUnit.SECONDS)

        verifyBlocking(delayedLoader, times(2)) { loadImage(any()) }

        coroutineContext.advanceTimeBy(3, TimeUnit.SECONDS)

        job.isCompleted `should be equal to` true

        coroutineContext.assertAllUnhandledExceptions { false }
    }
}