package tv.sporttotal.makers.dryrun

import android.app.onCreate
import android.app.onStart
import android.app.onStop
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import tv.sporttotal.makers.WorldCupActivity

class FourthTest {

    val activity = WorldCupActivity()

    @Nested
    inner class `When created`() {

        init {
            activity.onCreate(null)
        }

        @Test
        fun `should create tournament`() {
            activity.tournament.hasMatches `should be` true
        }

        @TestFactory
        fun `tournament should be stopped`() = notRunning

        @Nested
        inner class `When started` {
            init {
                activity.onStart()
            }

            @Test
            fun `should start tournament`() {
                activity.tournament.isRunning `should be` true
            }

            @Nested
            inner class `When stopped`() {
                init {
                    activity.onStop()
                }

                @TestFactory
                fun `tournament should be stopped`() = notRunning
            }
        }

        val notRunning = listOf(
            dynamicTest("tournament is stopped") {
                activity.tournament.isRunning `should be` false
        })
    }
}