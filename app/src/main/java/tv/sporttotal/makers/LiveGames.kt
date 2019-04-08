package tv.sporttotal.makers


class LiveGames() {
    private val games = mutableListOf<Game>()

    fun getGames() : List<Game> {
        if (games.isEmpty()) {
            throw IllegalArgumentException()
        }
        else return games
    }

    fun add(game: Game) {
        games.add(game)
    }

    var hasGames: Boolean = true
}