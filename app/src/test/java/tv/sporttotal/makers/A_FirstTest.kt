package tv.sporttotal.makers

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Test

class A_FirstTest {

    @Test
    fun test() {
        Tournament().hasGames `should be equal to` false
    }

    @Test
    fun test2() {

        // TODO wjhats wrong here?
//        assertFailsWith<IllegalArgumentException> { Tournament().getMatches() }

        { Tournament().getMatches() } `should throw` IllegalArgumentException::class
    }

    @Test
    fun testExtension() {

        val tested = Tournament()

        tested.givenGermanyPlaying()

        tested.getMatches() `should contain` Match("Germany", "France")

    }

}

private fun Tournament.givenGermanyPlaying() {
    this.add(Match("Germany", "France"))

}