package tv.sporttotal.makers


class Tournament() {
    private val games = mutableListOf<Match>()

    fun getMatches() : List<Match> {
        if (games.isEmpty()) {
            throw IllegalArgumentException()
        }
        else return games
    }

    fun add(match: Match) {
        games.add(match)
    }

    fun addSponsor(sponsor: Sponsor) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showBannersOf(sponsor: Sponsor) {
        sponsor.addImpressions(visitors)
    }

    var hasGames: Boolean = true

    val visitors: Int
        get() = games.calculateVisitors()

    var isRunning: Boolean = false
}

private fun MutableList<Match>.calculateVisitors(): Int = size * 100


