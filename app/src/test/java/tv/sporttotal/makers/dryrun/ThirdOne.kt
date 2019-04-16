package tv.sporttotal.makers.dryrun

import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.Team
import tv.sporttotal.makers.Tournament
import kotlin.test.assertEquals

class ThirdOne {

    @Test
    fun test() {

        playing {
            Team("Germany") vs Team("France")
        } during Tournament() `should increase visitors` true
    }

}

private infix fun Tournament.`should increase visitors`(boolean: Boolean) {
    assertEquals(boolean, this.visitors > 0)
}

private infix fun Match.during(tournament: Tournament) = tournament.also { it.add(this) }

private fun playing(function: () -> Match): Match = function()

private infix fun Team.vs(team: Team) = Match(this, team)
