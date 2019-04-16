package tv.sporttotal.makers

interface Sponsor {

    var isPremium: Boolean

    fun addImpressions(count: Int)

    fun getMoney() : Money
}