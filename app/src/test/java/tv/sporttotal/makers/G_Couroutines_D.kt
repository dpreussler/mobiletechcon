package tv.sporttotal.makers

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.test.withTestContext
import kotlinx.coroutines.withContext
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be greater or equal to`
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import java.util.concurrent.TimeUnit

@ObsoleteCoroutinesApi
class G_Couroutines_D : CoroutineScope {

    override val coroutineContext: TestCoroutineContext = TestCoroutineContext()

    @Test
    fun `advance and collect`() {

        val tested = ImageFilter(SimpleLoader())

        val job = launch {
            tested.loadAndCombine("1", "2").name `should be equal to` "1:2"
        }

        coroutineContext.advanceTimeTo(3, TimeUnit.SECONDS)
        coroutineContext.exceptions.forEach { throw it }
        job.isCompleted `should be equal to` true
    }

    @Test
    fun `advance to now`() {

        val tested = ImageFilter(SimpleLoader())

        val now = coroutineContext.now(TimeUnit.SECONDS)
        runBlocking(context = coroutineContext) {
            async {
                tested.loadAndCombine("1", "2").name `should be equal to` "1:2"
            }.await()
        }

        val later = coroutineContext.now(TimeUnit.SECONDS)
        later `should be greater or equal to` now + 3
    }

    @Test
    fun `advance step by step`() {

        // TODO why does this take so long?

        val imageLoader: ImageLoader = spy(DelayedLoader())
        val tested = ImageFilter(imageLoader)

        val job = launch {
            tested.loadAndCombine("1", "2").name `should be equal to` "1:2"
        }

        coroutineContext.advanceTimeTo(1, TimeUnit.SECONDS)

        verifyBlocking(imageLoader, times(2)) {
            loadImage(anyString())
        }

        coroutineContext.advanceTimeTo(4, TimeUnit.SECONDS)
        coroutineContext.exceptions.forEach { throw it }
        job.isCompleted `should be equal to` true
    }


    @Test
    fun `complex`() {

        // TODO this is weird :)
        val dispatchers = object : CoroutineDispatchers {
            override val default = TestCoroutineContext()
            override val io = TestCoroutineContext()
            override val main = TestCoroutineContext()
        }
        val tested = ImageFilter(SimpleLoader(), dispatchers)

        val job = launch {
            tested.loadAndCombine2("1", "2").name `should be equal to` "1:3"
        }

        coroutineContext.triggerActions()
        dispatchers.io.advanceTimeTo(3, TimeUnit.SECONDS)
        coroutineContext.triggerActions()

        coroutineContext.exceptions.forEach { throw it }
        dispatchers.io.exceptions.forEach { throw it }
        job.isCompleted `should be equal to` true

    }

    @Test
    fun `complex but better handling`() {

        val exceptions = mutableListOf<Throwable>()
        val handler = CoroutineExceptionHandler { _, exception ->
            exceptions.add(exception)
        }

        // TODO this is weird :)
        val dispatchers = object : CoroutineDispatchers {
            override val default = TestCoroutineContext()
            override val io = TestCoroutineContext()
            override val main = TestCoroutineContext()
        }
        val tested = ImageFilter(SimpleLoader(), dispatchers)

        val job = launch(handler) {
            tested.loadAndCombine2("1", "2").name `should be equal to` "1:3"
        }

        coroutineContext.triggerActions()
        dispatchers.io.advanceTimeTo(3, TimeUnit.SECONDS)
        coroutineContext.triggerActions()

        exceptions.forEach { throw it }
        job.isCompleted `should be equal to` true

    }

        class SimpleLoader : ImageLoader {
        override suspend fun loadImage(image: String) = Image(image)
    }

    inner class DelayedLoader : ImageLoader {
        override suspend fun loadImage(image: String) = Image(image).also { delay(1000) }
    }
}