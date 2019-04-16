package tv.sporttotal.makers.dryrun

import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainSome
import org.junit.jupiter.api.Test
import tv.sporttotal.makers.Team
import tv.sporttotal.makers.Tournament

class FirstTest {


    @Test
    fun `tournament should have games`() {
        val tested = Tournament()
        tested.hasGames `should be` true
    }

    @Test
    fun `test`() {
        val tested = Tournament()
        tested.getMatches() shouldContain Team("Germany")
    }

    @Test
    fun `should have some rockstars`() {
        val tested = Tournament()
        tested.getMatches() shouldContainSome  listOf(Team("Germany"), Team("France"))
    }

}