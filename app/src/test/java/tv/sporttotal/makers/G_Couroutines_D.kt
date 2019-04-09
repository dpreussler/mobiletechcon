package tv.sporttotal.makers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineContext
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

@UseExperimental(ObsoleteCoroutinesApi::class)
class G_Couroutines_D : CoroutineScope {

    override val coroutineContext: TestCoroutineContext = TestCoroutineContext()

    val tested = ImageFilter(DummyLoadder())

    @Test
    fun `advance and collect`() {
        launch {
            tested.loadAndCombine("1", "2").name `should be equal to` "1:2"
        }

        coroutineContext.advanceTimeTo(2, TimeUnit.SECONDS)
        coroutineContext.exceptions.forEach { throw it }
    }
}

private class DummyLoadder : ImageLoader {

    override suspend fun loadImage(image: String): Image {
        delay(1000)
        return Image(image)
    }
}
