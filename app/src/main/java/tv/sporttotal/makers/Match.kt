package tv.sporttotal.makers

class Match(val home: Team, val guest: Team) {
    fun isHightlight(): Boolean {
        return false
    }

    fun isPlaying(team: Team) = team in listOf(home, guest)

    var isToday = true
    var isFootball = true
}