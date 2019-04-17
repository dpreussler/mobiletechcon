package tv.sporttotal.makers.dryrun2

import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Match
import tv.sporttotal.makers.Team
import kotlin.test.assertEquals

class dsl {

    @Test
    fun test() {

        playing {
            Team("Germany") vs Team("France")
        } `should be in finals` true
    }
}

private infix fun Match.`should be in finals`(assertions: Boolean) {
    assertEquals(assertions, this.isHightlight())
}

private fun playing(function: () -> Match) = function()
private infix fun Team.vs(team: Team) = Match(this, team)
