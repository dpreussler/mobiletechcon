package tv.sporttotal.makers.dryrun

import com.nhaarman.mockitokotlin2.KStubbing
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.Sponsor
import tv.sporttotal.makers.Tournament

class SecondTest {

    @Test
    fun test() {

        val tested = Tournament()

        val sponsor = mock<Sponsor>()
        tested.addSponsor(sponsor)
        whenever(sponsor.isPremium).thenReturn(true)
    }

    @Test
    fun capturing() {

        val tested = Tournament()

        val sponsor = mock<Sponsor>()
        tested.addSponsor(sponsor)
        whenever(sponsor.isPremium).thenReturn(true)

        tested.showBannersOf(sponsor)

        argumentCaptor<Int>().apply {
            verify(sponsor).addImpressions(capture())
            firstValue `should be equal to` 100
        }
    }

    @Test
    fun complex() {

        val todaysMatch = mockTodaysGame {
            on {isFootball} doReturn true
        }
    }

    private fun mockTodaysGame(block: KStubbing<Match>.() -> Unit): Match =
        mock {
            on { isToday } doReturn true
        }
}
