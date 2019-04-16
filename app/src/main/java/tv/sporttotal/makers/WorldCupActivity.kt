package tv.sporttotal.makers

import android.app.Activity
import android.os.Bundle

class WorldCupActivity : Activity() {

    lateinit var tournament: Tournament

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tournament = Tournament()
    }

    override fun onStart() {
        super.onStart()
        tournament.isRunning = true
    }

    override fun onStop() {
        super.onStop()
        tournament.isRunning = false
    }
}
