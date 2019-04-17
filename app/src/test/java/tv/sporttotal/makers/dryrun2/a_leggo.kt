package tv.sporttotal.makers.dryrun2

import org.amshove.kluent.`should be`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.shouldContainSome
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Team
import tv.sporttotal.makers.Tournament

class blub {

    var tested = Tournament()

    @Test
    fun `should have no games initally`() {
        tested.hasMatches `should be` false
    }

    @Test
    fun `should have teams when adding matches`() {
        tested.add(Team("Germany"), Team("France"))
        tested.getTeams() shouldContainSome listOf(Team("Germany"), Team("France"))
    }

    @Test
    fun `should throw if no matches drawn`() {
        { tested.getMatches() } `should throw` IllegalArgumentException::class
    }
}