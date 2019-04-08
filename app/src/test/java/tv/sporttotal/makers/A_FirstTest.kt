package tv.sporttotal.makers

import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Test

class A_FirstTest {

    @Test
    fun test() {
        LiveGames().hasGames `should be equal to` false
    }

    @Test
    fun test2() {

        // TODO wjhats wrong here?
//        assertFailsWith<IllegalArgumentException> { LiveGames().getGames() }

        { LiveGames().getGames() } `should throw` IllegalArgumentException::class
    }

    @Test
    fun testExtension() {

        val tested = LiveGames()

        tested.givenGermanyPlaying()

        tested.getGames() `should contain` Game("Germany vs France")

    }

}

private fun LiveGames.givenGermanyPlaying() {
    this.add(Game("Germany vs France"))

}