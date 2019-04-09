package tv.sporttotal.makers

import com.nhaarman.mockitokotlin2.KStubbing
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class B_mocking {

    // TODO final issue
    // solutions: inteface, open, mockmaker, open For testing thingie, kotlintestrunner


    @Test
    fun simpleMocking() {
        val match = Mockito.mock(Match::class.java)
        Mockito.`when`(match.isHightlight()).thenReturn(true)
    }

    @Test
    fun simplerMocking() {
        val match = mock<Match>()
        whenever(match.isHightlight()).thenReturn(true)
    }

    @Test
    fun betterMocking() {
        val match = mock<Match> {
            on { isHightlight() } doReturn true
        }
    }

    @Test
    fun customMocking() {
        val match = mockFootballMatch {
            on { isHightlight() } doReturn true
        }
    }

    @Test
    fun arguments() {
        val match = mock<Match>()

        val team = Team("London")

        TeamScheduler(team, match, mock(), mock())

        val captor = argumentCaptor<Team>().apply {
            verify(match).isPlaying(capture())
        }

        captor.firstValue `should be` team

    }

    class TeamScheduler(val team: Team, vararg matches: Match) {
        private val games = mutableListOf<Match>()
        init {
            games.addAll(matches.filter { it.isPlaying(team) })
        }
    }

    private inline fun mockFootballMatch(block: KStubbing<Match>.() -> Any): Match {
        return mock<Match> {
            on { isFootball } doReturn true
        }.also { block(KStubbing(it)) }

    }
}